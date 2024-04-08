package com.example.waitTimeServer.service;
import com.example.waitTimeServer.dto.ClientRequest;
import com.example.waitTimeServer.model.ClientInstance;
import com.example.waitTimeServer.model.Ping;
import com.example.waitTimeServer.model.WeeklyHourlyBin;
import com.example.waitTimeServer.repository.ClientInstanceRepository;
import com.example.waitTimeServer.repository.PingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class ApiService {

    @Autowired
    private ClientInstanceRepository clientRepository;
    @Autowired
    private PingRepository pingRepository;
    @Autowired
    private  MongoTemplate mongoTemplate;



    public List<Ping> findPingsByClientIdInRange(String clientId, LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            // No specific range provided, find all pings for the clientId
            return pingRepository.findByClientId(clientId);
        } else {
            // Range provided, find pings for the clientId within the specified range
            return pingRepository.findByClientIdInRange(clientId, start, end);
        }
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
        return sum  / pings.size();
    }
    public List<WeeklyHourlyBin> getPingsGroupedByDayOfWeekAndHour(String clientId) {
        // Project to extract day of the week and hour
        ProjectionOperation projectToDayOfWeekAndHour = Aggregation.project()
                .andExpression("dayOfWeek(timestamp)").as("dayOfWeek")
                .andExpression("hour(timestamp)").as("hour")
                .andInclude("value");

        // Group by day of the week and hour, then average the value
        GroupOperation groupByDayOfWeekAndHour = Aggregation.group("dayOfWeek", "hour")
                .avg("value").as("averageValue");

        // Sort by day of the week and hour for logical ordering
        SortOperation sortByDayOfWeekAndHour = Aggregation.sort(Sort.by(Sort.Direction.ASC, "dayOfWeek", "hour"));

        // Define the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(
                match(Criteria.where("clientId").is(clientId)), // Filter by clientId if needed
                projectToDayOfWeekAndHour,
                groupByDayOfWeekAndHour,
                sortByDayOfWeekAndHour
        );

        // Execute the aggregation
        AggregationResults<WeeklyHourlyBin> result = mongoTemplate.aggregate(aggregation, Ping.class, WeeklyHourlyBin.class);

        // Return the mapped results
        return result.getMappedResults();
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
