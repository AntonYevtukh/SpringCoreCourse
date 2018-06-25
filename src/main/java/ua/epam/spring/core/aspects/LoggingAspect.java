package ua.epam.spring.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Aspect
@Component("loggingAspect")
public class LoggingAspect {

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods() {}

    @Pointcut("allLogEventMethods() && within(*.*File*Logger)")
    private void logEventsInsideFileLoggers() {}

    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint) {
        //System.out.println("Before: " + joinPoint.getTarget().getClass().getSimpleName() + " " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "allLogEventMethods()", returning = "returnedValue")
    public void logAfter(Object returnedValue) {
        //System.out.println("Returned value: " + returnedValue);
    }

    @AfterThrowing(pointcut = "allLogEventMethods()", throwing = "throwable")
    public void logAfterThrowing(Throwable throwable) {
        //System.out.println("Exception: " + throwable);
    }

}
