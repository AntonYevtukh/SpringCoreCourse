package ua.epam.spring.core.loggers;

import ua.epam.spring.core.beans.Event;

import java.text.DateFormat;

public class ConsoleEventLogger implements EventLogger{

    @Override
    public void logEvent(Event event) {
        System.out.println(event);
    }
}
