package com.example.adapter.output.repository;


import com.example.adapter.output.repository.mapper.UserOutputMapper;
import com.example.adapter.output.repository.utils.Constants;
import com.example.core.User;
import com.example.core.ports.input.FindUserByIdInputPort;
import com.example.core.ports.output.FindUserByIdOutputPort;
import com.example.core.ports.output.FindUserByNameOutputPort;
import com.example.core.ports.output.SaveUserOutputPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.example.adapter.output.repository.utils.Constants.IGNORE_CASE;
import static com.example.adapter.output.repository.utils.Constants.REGEX_ANY;
import static com.example.adapter.output.repository.utils.Constants.REGEX_START;

@Slf4j
@Component
@AllArgsConstructor
public class UserRepositoryImpl implements SaveUserOutputPort, FindUserByIdOutputPort, FindUserByNameOutputPort {

    private final UserOutputMapper mapper;

    private final UserEntityRepository repository;

    @Override
    public User save(User user) {

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Início da persistência de um usuário ", Constants.LOG_METHOD_SAVE, user);

        var entity = mapper.toUserEntity(user);

        entity = repository.save(entity);

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Fim da persistência de um usuário ", Constants.LOG_METHOD_SAVE, entity);

        return mapper.toDomain(entity);
    }

    @Override
    public Optional<User> findById(String userId) {
        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY_ID,
                "Início da busca de um usuário por id ", Constants.LOG_METHOD_FIND_BY_ID, userId);

        var entity = repository.findById(userId);

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Fim da busca de um usuário por id ", Constants.LOG_METHOD_FIND_BY_ID, entity);

        return entity.map(mapper::toDomain);
    }

    @Override
    public List<User> findUserIdsByName(String name) {
        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY_ID,
                "Início da busca de um usuário por nome ", Constants.LOG_METHOD_FIND_BY_NAME, name);

        var entity = repository.findByNameRegexIgnoreCase(IGNORE_CASE.concat(REGEX_START).concat(name).concat(REGEX_ANY));

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Fim da busca de um usuário por nome ", Constants.LOG_METHOD_FIND_BY_NAME, entity);

        return entity.stream().map(mapper::toDomain).toList();
    }
}


