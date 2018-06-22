package ua.epam.spring.core.loggers;

import ua.epam.spring.core.beans.Event;

public interface EventLogger {
    void logEvent(Event event);
}
