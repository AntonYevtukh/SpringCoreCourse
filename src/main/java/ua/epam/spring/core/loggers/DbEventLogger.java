package ua.epam.spring.core.loggers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.epam.spring.core.beans.Event;

@Component("dbEventLogger")
public class DbEventLogger implements EventLogger {

    private JdbcTemplate jdbcTemplate;

    public DbEventLogger(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void logEvent(Event event) {
        jdbcTemplate.update("INSERT INTO events(message) VALUES (?)", event.getMessage());
    }
}
