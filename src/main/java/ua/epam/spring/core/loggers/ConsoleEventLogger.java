package ua.epam.spring.core.loggers;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import ua.epam.spring.core.beans.Event;

import java.text.DateFormat;

@Component("consoleEventLogger")
@EnableAspectJAutoProxy
public class ConsoleEventLogger implements EventLogger{

    @Override
    public void logEvent(Event event) {
        System.out.println(event);
    }
}
