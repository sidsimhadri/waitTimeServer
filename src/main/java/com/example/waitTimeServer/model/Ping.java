package com.example.waitTimeServer.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Data
@Builder
@Document(collection = "pings")
public class Ping {

    @Id
    private String Id;
    private String clientId;
    private LocalDateTime timestamp;
    private Double value;
}
