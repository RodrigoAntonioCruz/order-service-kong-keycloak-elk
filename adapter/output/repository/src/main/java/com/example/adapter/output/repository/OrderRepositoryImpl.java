package com.example.adapter.output.repository;

import com.example.adapter.output.repository.entity.OrderEntity;
import com.example.adapter.output.repository.mapper.OrderOutputMapper;
import com.example.adapter.output.repository.utils.Constants;
import com.example.core.Order;
import com.example.core.OrderDetails;
import com.example.core.Orders;
import com.example.core.User;
import com.example.core.ports.input.FindProductByIdInputPort;
import com.example.core.ports.input.FindUserByIdInputPort;
import com.example.core.ports.input.FindUserByNameInputPort;
import com.example.core.ports.output.DeleteOrderOutputPort;
import com.example.core.ports.output.FindOrderByFilterOutputPort;
import com.example.core.ports.output.SaveOrderOutputPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Decimal128;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.adapter.output.repository.utils.Constants.COMMA;
import static com.example.adapter.output.repository.utils.Constants.DATE;
import static com.example.adapter.output.repository.utils.Constants.NO_MATCH;
import static com.example.adapter.output.repository.utils.Constants.ORDER_ID;
import static com.example.adapter.output.repository.utils.Constants.PAD_FORMAT;
import static com.example.adapter.output.repository.utils.Constants.PROCESSED;
import static com.example.adapter.output.repository.utils.Constants.REGEX_ANY;
import static com.example.adapter.output.repository.utils.Constants.REGEX_DIGITS;
import static com.example.adapter.output.repository.utils.Constants.REGEX_START;
import static com.example.adapter.output.repository.utils.Constants.TOTAL;
import static com.example.adapter.output.repository.utils.Constants.USER_ID;

@Slf4j
@Component
@AllArgsConstructor
public class OrderRepositoryImpl implements SaveOrderOutputPort, FindOrderByFilterOutputPort, DeleteOrderOutputPort {

    private final OrderOutputMapper mapper;

    private final MongoTemplate mongoTemplate;

    private final FindUserByIdInputPort findUserByIdInputPort;

    private final FindUserByNameInputPort findUserByNameInputPort;

    private final FindProductByIdInputPort findProductByIdInputPort;

    @Override
    public Order save(Order order) {
        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Início da persistência de um pedido ", Constants.LOG_METHOD_SAVE, order);

        var entity = mapper.toOrderEntity(order);
        entity = mongoTemplate.save(entity);

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Fim da persistência de um pedido ", Constants.LOG_METHOD_SAVE, entity);

        return mapper.toDomain(entity);
    }

    @Override
    public void deleteById(String orderId) {
        log.info("Início da exclusão do registro: " + orderId);

        mongoTemplate.remove(new Query(Criteria
                .where(ORDER_ID).is(orderId)), OrderEntity.class);

        log.info("Fim da exclusão do registro: " + orderId);
    }

    @Override
    public List<Order> findAllPaged(int skip, int limit) {
        Query query = new Query().skip(skip).limit(limit);
        return mongoTemplate.find(query, OrderEntity.class).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsProcessed(String orderId) {
        Query query = new Query(Criteria.where(ORDER_ID).is(orderId));
        Update update = new Update().set(PROCESSED, true);
        mongoTemplate.updateMulti(query, update, OrderEntity.class);
    }

    @Override
    @Cacheable(value = "orders", key = "#query")
    public List<OrderDetails> findByFilter(String query) {
        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Início da busca de um pedido por filtros ", Constants.LOG_METHOD_FIND_BY_FILTER, query);

        Query mongoQuery = new Query();
        mongoQuery.addCriteria(Criteria.where(PROCESSED).is(true));

        if (query.contains(COMMA)) {
            // Consulta por intervalo de datas ou valores
            String[] parts = query.split(COMMA);
            if (!tryAddDateRangeCriteria(mongoQuery, parts)) {
                tryAddValueRangeCriteria(mongoQuery, parts);
            }
        } else if (isNumeric(query)) {
            // Consulta por ID (como String ou Long)
            String queryRegex = REGEX_START.concat(String.format(PAD_FORMAT, Long.parseLong(query))).concat(REGEX_ANY);
            mongoQuery.addCriteria(new Criteria().orOperator(
                    Criteria.where(ORDER_ID).regex(queryRegex),
                    Criteria.where(USER_ID).regex(queryRegex)
            ));
        } else {
            // Consulta aproximada por nome ou outros campos
            List<User> users = findUserByNameInputPort.findUserIdsByName(query);
            if (!users.isEmpty()) {
                List<String> userIds = users.stream().map(User::getId).collect(Collectors.toList());
                mongoQuery.addCriteria(Criteria.where(USER_ID).in(userIds));
            } else {
                mongoQuery.addCriteria(Criteria.where(USER_ID).is(NO_MATCH));
            }
        }

        var orderEntities = mongoTemplate.find(mongoQuery, OrderEntity.class);

        var orderDetailsList = orderEntities.stream()
                .map(this::mapToOrderDetails)
                .collect(Collectors.toList());

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Fim da busca de um pedido por filtros ", Constants.LOG_METHOD_FIND_BY_FILTER, orderDetailsList);

        return orderDetailsList;
    }

    private boolean isNumeric(String str) {
        return str.matches(REGEX_DIGITS);
    }

    private boolean tryAddDateRangeCriteria(Query mongoQuery, String[] parts) {
        try {
            LocalDate startDate = LocalDate.parse(parts[0].trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate endDate = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            mongoQuery.addCriteria(Criteria.where(DATE).gte(startDate).lte(endDate));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void tryAddValueRangeCriteria(Query mongoQuery, String[] parts) {
        try {
            BigDecimal minValue = new BigDecimal(parts[0].trim());
            BigDecimal maxValue = new BigDecimal(parts[1].trim());
            mongoQuery.addCriteria(Criteria.where(TOTAL).gte(new Decimal128(minValue)).lte(new Decimal128(maxValue)));
        } catch (Exception e) {
            log.error("Erro ao analisar a consulta de faixa de valores: {}", String.join(COMMA, parts), e);
        }
    }

    private OrderDetails mapToOrderDetails(OrderEntity entity) {
        var user = findUserByIdInputPort.findById(entity.getUserId());
        var products = entity.getProductIds().stream()
                .map(findProductByIdInputPort::findById)
                .collect(Collectors.toList());

        Orders order = new Orders();
        order.setOrderId(entity.getOrderId());
        order.setTotal(entity.getTotal());
        order.setDate(entity.getDate());
        order.setProducts(products);

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setId(user.getId());
        orderDetails.setName(user.getName());
        orderDetails.setOrders(List.of(order));

        return orderDetails;
    }
}
