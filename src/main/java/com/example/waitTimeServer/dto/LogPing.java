package com.example.waitTimeServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

public class LogPing {
    private String clientId;
    private LocalDateTime timestamp;
    private double value;
}
