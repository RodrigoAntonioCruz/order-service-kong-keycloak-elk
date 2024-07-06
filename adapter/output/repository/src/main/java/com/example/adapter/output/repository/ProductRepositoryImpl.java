package com.example.adapter.output.repository;


import com.example.adapter.output.repository.mapper.ProductOutputMapper;
import com.example.adapter.output.repository.utils.Constants;
import com.example.core.Product;
import com.example.core.ports.output.FindProductByIdOutputPort;
import com.example.core.ports.output.SaveProductOutputPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class ProductRepositoryImpl implements SaveProductOutputPort, FindProductByIdOutputPort {

    private final ProductOutputMapper mapper;

    private final ProductEntityRepository repository;
    @Override
    public Product save(Product product) {

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Início da persistência de um produto ", Constants.LOG_METHOD_SAVE, product);

        var entity = mapper.toProductEntity(product);

        entity = repository.save(entity);

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Fim da persistência de um produto ", Constants.LOG_METHOD_SAVE, entity);

        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Product> findById(String productId) {
        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY_ID,
                "Início da busca de um produto por id ", Constants.LOG_METHOD_FIND_BY_ID, productId);

        var entity = repository.findById(productId);

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Fim da busca de um produto por id ", Constants.LOG_METHOD_FIND_BY_ID, entity);

        return entity.map(mapper::toDomain);
    }
}


