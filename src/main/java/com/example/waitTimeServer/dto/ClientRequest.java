package com.example.waitTimeServer.dto;

import lombok.Data;

@Data
public class ClientRequest {
    private String name;
    private String type;
    private String configuration;
}
