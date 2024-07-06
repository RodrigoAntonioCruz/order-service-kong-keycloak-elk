package com.example.adapter.output.repository.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@Document(collection = "users")
public class UserEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4193301264047593807L;

    @Id
    private String id;
    private String name;

}
