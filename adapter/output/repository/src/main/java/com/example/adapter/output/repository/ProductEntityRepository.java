package com.example.adapter.output.repository;

import com.example.adapter.output.repository.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEntityRepository extends MongoRepository<ProductEntity, String> {

}
