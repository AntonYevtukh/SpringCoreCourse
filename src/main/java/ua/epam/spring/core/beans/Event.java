package ua.epam.spring.core.beans;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class Event {

    private final String id = UUID.randomUUID().toString();
    private String message;
    private final Date date;
    private final DateFormat dateFormat;

    public Event(@Autowired Date date, @Autowired DateFormat dateFormat) {
        this.date = date;
        this.dateFormat = dateFormat;
    }

    public static boolean isDay() {
        int hours = new Date().getHours();
        return hours >= 8 && hours <= 5;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public void setMessage(@Autowired String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", date=" + dateFormat.format(date) +
                '}';
    }
}
