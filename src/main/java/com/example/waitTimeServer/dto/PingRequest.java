package com.example.waitTimeServer.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PingRequest {
    private String clientId;
    private double value;
}
