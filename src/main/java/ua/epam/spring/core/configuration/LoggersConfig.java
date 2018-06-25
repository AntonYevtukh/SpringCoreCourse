package ua.epam.spring.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ua.epam.spring.core.beans.Event;
import ua.epam.spring.core.beans.EventType;
import ua.epam.spring.core.loggers.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import static ua.epam.spring.core.beans.EventType.*;

@Configuration
public class LoggersConfig {

    @Bean
    public List<EventLogger> eventLoggerList(@Autowired @Qualifier("consoleEventLogger") EventLogger eventLoggerOne,
                                             @Autowired @Qualifier("fileEventLogger") EventLogger eventLoggerTwo,
                                             @Autowired @Qualifier("dbEventLogger") EventLogger eventLoggerThree) {
        List<EventLogger> loggerList = List.of(eventLoggerOne, eventLoggerTwo, eventLoggerThree);
        //loggerList.forEach((EventLogger eventLogger) -> System.out.println(eventLogger.getClass().getName()));
        return loggerList;
    }

    @Bean
    public Map<EventType, EventLogger> eventTypeLoggerMap(@Autowired @Qualifier("consoleEventLogger") EventLogger infoEventLogger,
                                                          @Autowired @Qualifier("combinedEventLogger") EventLogger errorEventLogger) {
        Map<EventType, EventLogger> eventTypeLoggerMap = new EnumMap<>(EventType.class);
        eventTypeLoggerMap.put(INFO, infoEventLogger);
        eventTypeLoggerMap.put(ERROR, errorEventLogger);
        return eventTypeLoggerMap;
    }
}
