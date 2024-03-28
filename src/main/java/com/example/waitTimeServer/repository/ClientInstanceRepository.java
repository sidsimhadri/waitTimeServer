package com.example.waitTimeServer.repository;

import com.example.waitTimeServer.model.ClientInstance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientInstanceRepository extends MongoRepository<ClientInstance, String> {
    // Define custom query methods if needed
}