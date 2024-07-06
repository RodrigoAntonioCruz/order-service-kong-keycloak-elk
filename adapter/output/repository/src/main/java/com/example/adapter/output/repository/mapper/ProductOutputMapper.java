package com.example.adapter.output.repository.mapper;


import com.example.adapter.output.repository.entity.ProductEntity;
import com.example.core.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductOutputMapper {
    Product toDomain(ProductEntity productEntity);
    ProductEntity toProductEntity(Product product);
}

