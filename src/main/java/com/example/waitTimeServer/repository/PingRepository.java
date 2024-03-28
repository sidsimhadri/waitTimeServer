package com.example.waitTimeServer.repository;

import com.example.waitTimeServer.model.ClientInstance;
import com.example.waitTimeServer.model.Ping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PingRepository extends MongoRepository<ClientInstance, String> {
    @Query("{'clientId': ?0, 'timestamp': {$gte: ?1, $lte: ?2}}")
    List<Ping> findByClientIdInRange(String clientId, LocalDateTime start, LocalDateTime end);
}