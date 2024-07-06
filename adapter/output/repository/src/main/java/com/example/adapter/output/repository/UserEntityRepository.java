package com.example.adapter.output.repository;

import com.example.adapter.output.repository.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityRepository extends MongoRepository<UserEntity, String> {
    List<UserEntity> findByNameRegexIgnoreCase(String regex);

}
