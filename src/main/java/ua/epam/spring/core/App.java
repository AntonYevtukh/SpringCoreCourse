package ua.epam.spring.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.epam.spring.core.beans.Client;
import ua.epam.spring.core.beans.Event;
import ua.epam.spring.core.loggers.EventLogger;

public class App {

    private Client client;
    private EventLogger eventLogger;
    private static ConfigurableApplicationContext configurableApplicationContext;

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }
    //
    public static void main(String[] args) {
        configurableApplicationContext =
                new ClassPathXmlApplicationContext("spring.xml");
        App app = configurableApplicationContext.getBean(App.class);
        app.logEvent("User 1 created!");
        app.logEvent("User 2 created!");
        configurableApplicationContext.close();
    }

    private void logEvent(String message) {
        String newMessage = message.replaceAll(client.getId(), client.getFullName());
        Event event = configurableApplicationContext.getBean(Event.class);
        event.setMessage(newMessage);
        eventLogger.logEvent(event);
    }
}
