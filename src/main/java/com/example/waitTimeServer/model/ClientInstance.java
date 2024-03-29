package com.example.waitTimeServer.model;
import com.example.waitTimeServer.dto.ClientRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "clients")
public class ClientInstance {
    @Id
    private String Id;

    private String name;

    private String type;

    private String configuration;

    public ClientInstance(ClientRequest registrationRequest) {
        this.name = registrationRequest.getName();
        this.type = registrationRequest.getType();
        this.configuration = registrationRequest.getConfiguration();
    }
}
