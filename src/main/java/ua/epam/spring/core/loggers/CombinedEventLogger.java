package ua.epam.spring.core.loggers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ua.epam.spring.core.beans.Event;

import java.util.List;

@Component("combinedEventLogger")
public class CombinedEventLogger implements EventLogger {

    private List<EventLogger> loggers;

    public CombinedEventLogger(@Autowired @Qualifier("eventLoggerList") List<EventLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        loggers.forEach((EventLogger eventLogger) -> eventLogger.logEvent(event));
    }
}
