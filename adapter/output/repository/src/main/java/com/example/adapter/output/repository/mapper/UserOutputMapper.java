package com.example.adapter.output.repository.mapper;


import com.example.adapter.output.repository.entity.UserEntity;
import com.example.core.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserOutputMapper {
    User toDomain(UserEntity userEntity);
    UserEntity toUserEntity(User user);
}

