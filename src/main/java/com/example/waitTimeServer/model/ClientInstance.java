package com.example.waitTimeServer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "clients")
public class ClientInstance {
    @Id
    private String Id;

    private String name;

    private String type;

    private String configuration;
}
