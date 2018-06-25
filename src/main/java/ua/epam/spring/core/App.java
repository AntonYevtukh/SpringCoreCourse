package ua.epam.spring.core;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ua.epam.spring.core.aspects.StatisticsAspect;
import ua.epam.spring.core.beans.Client;
import ua.epam.spring.core.beans.Event;
import ua.epam.spring.core.beans.EventType;
import ua.epam.spring.core.configuration.AppConfig;
import ua.epam.spring.core.configuration.LoggersConfig;
import ua.epam.spring.core.loggers.EventLogger;

import java.util.Arrays;
import java.util.Map;

import static ua.epam.spring.core.beans.EventType.*;

public class App {

    private Client client;
    private EventLogger defaultEventLogger;
    private Map<EventType, EventLogger> eventTypeLoggerMap;
    private static AnnotationConfigApplicationContext annotationConfigApplicationContext;

    public App(Client client, EventLogger defaultEventLogger,
               Map<EventType, EventLogger> eventTypeLoggerMap) {
        this.client = client;
        this.defaultEventLogger = defaultEventLogger;
        this.eventTypeLoggerMap = eventTypeLoggerMap;
    }

    public static void main(String[] args) {
        annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.scan("ua.epam.spring.core");
        annotationConfigApplicationContext.refresh();
        App app = annotationConfigApplicationContext.getBean(App.class);
        //PropertySourcesPlaceholderConfigurer configurer = (PropertySourcesPlaceholderConfigurer)annotationConfigApplicationContext.getBean("configurer");
        //System.out.println(configurer);
        app.logEvent("User 1 created!", INFO);
        app.logEvent(app.client.getGreeting(), INFO);
        app.logEvent("User 2 created!", ERROR);
        annotationConfigApplicationContext.close();
    }

    private void logEvent(String message, EventType eventType) {
        String newMessage = "[" + eventType + "] " + message.replaceAll(client.getId(), client.getFullName());
        Event event = annotationConfigApplicationContext.getBean(Event.class);
        event.setMessage(newMessage);
        EventLogger eventLogger = eventTypeLoggerMap.getOrDefault(eventType, defaultEventLogger);
        eventLogger.logEvent(event);
    }
}
