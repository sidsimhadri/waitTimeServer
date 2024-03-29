package com.example.waitTimeServer.model;
import com.example.waitTimeServer.dto.ClientRequest;
import lombok.Data;
import lombok.NoArgsConstructor; // Import Lombok's NoArgsConstructor
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "clients")
public class ClientInstance {
    @Id
    private String id;

    private String name;

    private String type;

    private String configuration;
    public void initializeFromClientRequest(ClientRequest registrationRequest) {
        this.name = registrationRequest.getName();
        this.type = registrationRequest.getType();
        this.configuration = registrationRequest.getConfiguration();
    }
    public ClientInstance(String id, ClientRequest registrationRequest) {
        this.id = id;
        initializeFromClientRequest(registrationRequest);
    }
}
