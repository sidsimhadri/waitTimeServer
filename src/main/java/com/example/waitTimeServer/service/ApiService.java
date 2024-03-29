package com.example.waitTimeServer.service;
import com.example.waitTimeServer.dto.ClientRequest;
import com.example.waitTimeServer.model.ClientInstance;
import com.example.waitTimeServer.model.Ping;
import com.example.waitTimeServer.repository.ClientInstanceRepository;
import com.example.waitTimeServer.repository.PingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApiService {

    @Autowired
    private ClientInstanceRepository clientRepository;
    @Autowired
    private PingRepository pingRepository;

    public List<Ping> findPingsByClientIdInRange(String clientId, LocalDateTime start, LocalDateTime end) {
        return pingRepository.findByClientIdInRange(clientId, start, end);
    }
    public List<ClientInstance> getAllClients() {
        return clientRepository.findAll();
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


    public String launchClient(ClientRequest launchRequest) {
        String scriptPath = "../videoEdge/main.py";
        String[] pythonCommand = {"python", scriptPath, launchRequest.getType(), "mp4", launchRequest.getName()};

        ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand);

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Python script started successfully.";
            } else {
                return "Failed to start Python script. Exit code: " + exitCode;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Failed to launch client: " + e.getMessage();
        }
    }

}
