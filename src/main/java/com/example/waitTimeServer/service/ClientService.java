package com.example.waitTimeServer.service;
import com.example.waitTimeServer.dto.ClientRequest;
import com.example.waitTimeServer.dto.PingRequest;
import com.example.waitTimeServer.model.ClientInstance;
import com.example.waitTimeServer.model.Ping;
import com.example.waitTimeServer.repository.ClientInstanceRepository;
import com.example.waitTimeServer.repository.PingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientInstanceRepository clientRepository;
    @Autowired
    private PingRepository pingRepository;


    public ClientInstance registerClient(String id, ClientRequest registrationRequest) {
        return clientRepository.save(new ClientInstance(id, registrationRequest));
    }

    public Ping logPing(PingRequest pingRequest) {
        Optional<ClientInstance> maybeClient = this.clientRepository.findById(pingRequest.getClientId());
        Ping newPing = new Ping(pingRequest);
        if(maybeClient.isPresent()){
            ClientInstance client = maybeClient.get();
            client.setLastPing(newPing);
            this.clientRepository.save(client);
        }
        return pingRepository.save(newPing);
    }
}
