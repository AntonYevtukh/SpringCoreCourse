package ua.epam.spring.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.epam.spring.core.beans.Event;
import ua.epam.spring.core.loggers.ConsoleEventLogger;
import ua.epam.spring.core.loggers.EventLogger;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component("statisticsAspect")
public class StatisticsAspect {

    //@Autowired
    private Map<Class<? extends EventLogger>, Long> statisticsMap = new HashMap<>();
    @Value("1")
    private long maxAllowedCount;
    @Autowired @Qualifier("fileEventLogger")
    private EventLogger anotherLogger;

    @AfterReturning(pointcut = "allLogEventMethods()")
    public void countStatistics(JoinPoint joinPoint) {
        Class<? extends EventLogger> clazz = (Class<? extends EventLogger>) joinPoint.getTarget().getClass();
        statisticsMap.compute(clazz,
                (Class<? extends EventLogger> keyClass, Long oldCount) -> (oldCount == null) ? 1 : oldCount + 1);
    }

    @Around("consoleLoggerMethod() && args(event)")
    public void aroundLogEvent(ProceedingJoinPoint proceedingJoinPoint, Event event) throws Throwable {
        System.out.println("Event object: " + event);
        if (statisticsMap.getOrDefault(ConsoleEventLogger.class, 0L) < maxAllowedCount) {
            proceedingJoinPoint.proceed(new Object[] {event});
        } else {
            anotherLogger.logEvent(event);
        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Statistics: " + statisticsMap);
    }

    @Pointcut("execution(* ua.epam.spring.core.loggers.*.logEvent(..))")
    private void allLogEventMethods() {
    }

    @Pointcut("execution(* ua.epam.spring.core.loggers.ConsoleEventLogger.*(..))")
    private void consoleLoggerMethod() {
    }
}
