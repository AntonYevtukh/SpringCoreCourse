package ua.epam.spring.core.beans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Client {

    private String id;
    private String fullName;
    private String greeting;
    @Autowired
    private Environment environment;

    public Client(@Value("${client.id}") String id, @Value("${client.name}") String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGreeting() {
        return greeting;
    }

    @Autowired
    public void setGreeting(@Value("#{'Hello, ' + systemEnvironment['USERNAME']}") String greeting) {
        this.greeting = greeting;
    }
}
