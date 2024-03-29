package com.example.waitTimeServer.model;
import com.example.waitTimeServer.dto.ClientRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "clients")
public class ClientInstance {
    @Id
    private String id;

    private String name;

    private String type;

    private String configuration;

    public ClientInstance(String id, ClientRequest registrationRequest) {
        this.id = id;
        this.name = registrationRequest.getName();
        this.type = registrationRequest.getType();
        this.configuration = registrationRequest.getConfiguration();
    }
}
