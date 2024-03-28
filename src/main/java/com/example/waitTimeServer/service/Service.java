package com.example.waitTimeServer.service;
import com.example.waitTimeServer.dto.LaunchClientRequest;
import com.example.waitTimeServer.model.ClientInstance;
import com.example.waitTimeServer.model.Ping;
import com.example.waitTimeServer.repository.ClientInstanceRepository;
import com.example.waitTimeServer.repository.PingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    private ClientInstanceRepository clientRepository;
    private PingRepository pingRepository;

    public void registerClient(ClientInstance client) {
        clientRepository.save(client);
    }
    public List<Ping> findPingsByClientIdInRange(String clientId, LocalDateTime start, LocalDateTime end) {
        return pingRepository.findByClientIdInRange(clientId, start, end);
    }
    public List<ClientInstance> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<ClientInstance> getClientById(String id) {
        return clientRepository.findById(id);
    }

    public Double currentData(String clientId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinutesAgo = now.minusMinutes(5);
        List<Ping> pings =  pingRepository.findByClientIdInRange(clientId, now, fiveMinutesAgo);
        double sum = 0;
        for (Ping p :pings){
            sum += p.getValue();
        }
        return sum/pings.size();
    }

    public String launchClientAndSave(LaunchClientRequest launchRequest) {
    }
}
