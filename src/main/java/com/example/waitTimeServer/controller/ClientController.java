package com.example.waitTimeServer.controller;
import com.example.waitTimeServer.dto.ClientRequest;
import com.example.waitTimeServer.dto.PingRequest;
import com.example.waitTimeServer.model.ClientInstance;
import com.example.waitTimeServer.model.Ping;
import com.example.waitTimeServer.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/client")
    public ResponseEntity<String> registerClient(
            @PathVariable ClientRequest registrationRequest) {

        ClientInstance newClient = clientService.registerClient(registrationRequest);
        if (newClient != null) {
            return ResponseEntity.ok(newClient.getId());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/client")
    public ResponseEntity<String> logPing(@PathVariable PingRequest pingRequest) {
        Ping ping = clientService.logPing(pingRequest);
        if (ping != null) {
            return ResponseEntity.ok(ping.getId());
        } else {
            return ResponseEntity.noContent().build();
        }
    }


}