package com.example.waitTimeServer.dto;

import lombok.Data;

@Data
public class LaunchClientRequest {
    private String name;
    private String type;
    private String configuration;
}
