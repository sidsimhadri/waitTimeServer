package com.example.waitTimeServer.model;
import com.example.waitTimeServer.dto.PingRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Document(collection = "pings")
public class Ping {

    @Id
    private String Id;
    private String clientId;
    private LocalDateTime timestamp;
    private Double value;

    public Ping(PingRequest ping) {
        this.clientId = ping.getClientId();
        this.timestamp = LocalDateTime.now();
        this.value = ping.getValue();
    }
}
