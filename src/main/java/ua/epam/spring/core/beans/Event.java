package ua.epam.spring.core.beans;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class Event {

    private final String id = UUID.randomUUID().toString();
    private String message;
    private final Date date;
    private final DateFormat dateFormat;

    public Event(Date date, DateFormat dateFormat) {
        this.date = date;
        this.dateFormat = dateFormat;
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

    public void setMessage(String message) {
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
