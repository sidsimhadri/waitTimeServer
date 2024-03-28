package com.example.waitTimeServer.controller;
import com.example.waitTimeServer.dto.LaunchClientRequest;
import com.example.waitTimeServer.model.Ping;
import com.example.waitTimeServer.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;
@RestController
public class FrontendController {

    @Autowired
    private Service service;

    @GetMapping("/admin/{clientId}")
    public ResponseEntity<List<Ping>> getPingsByClientIdInRange(
            @PathVariable String clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<Ping> pings = service.findPingsByClientIdInRange(clientId, start, end);
        return ResponseEntity.ok(pings);
    }

    @GetMapping("/admin/{clientId}/current")
    public ResponseEntity<Double> getCurrentData(@PathVariable String clientId) {
        Double average = service.currentData(clientId);
        if (average != null) {
            return ResponseEntity.ok(average);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/admin/launch")
    public ResponseEntity<String> launchClient(@PathVariable LaunchClientRequest launchRequest) {
        String id = service.launchClientAndSave(launchRequest);
        if (id != null) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}