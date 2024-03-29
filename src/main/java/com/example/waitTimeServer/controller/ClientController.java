package com.example.waitTimeServer.controller;
import com.example.waitTimeServer.dto.ClientRequest;
import com.example.waitTimeServer.dto.PingRequest;
import com.example.waitTimeServer.model.ClientInstance;
import com.example.waitTimeServer.model.Ping;
import com.example.waitTimeServer.service.ClientService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import org.slf4j.Logger;

@RestController
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientService clientService;

    @PostMapping("/client")
    public ResponseEntity<String> registerClient(
            @RequestBody ClientRequest registrationRequest) {
        String id = UUID.randomUUID().toString();
        ClientInstance newClient = clientService.registerClient(id, registrationRequest);
        if (newClient != null) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/client/ping")
    public ResponseEntity<String> logPing(@RequestBody PingRequest pingRequest) {
        Ping ping = clientService.logPing(pingRequest);
        if (ping != null) {
            return ResponseEntity.ok(ping.getId());
        } else {
            return ResponseEntity.noContent().build();
        }
    }


}