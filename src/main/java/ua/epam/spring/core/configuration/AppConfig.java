package ua.epam.spring.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.epam.spring.core.App;
import ua.epam.spring.core.beans.Client;
import ua.epam.spring.core.beans.Event;
import ua.epam.spring.core.beans.EventType;
import ua.epam.spring.core.loggers.CombinedEventLogger;
import ua.epam.spring.core.loggers.ConsoleEventLogger;
import ua.epam.spring.core.loggers.EventLogger;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.util.Date;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import static ua.epam.spring.core.beans.EventType.*;
import static java.text.DateFormat.SHORT;

@Configuration
@Import(LoggersConfig.class)
public class AppConfig {

    @Bean(name = "defaultEventLogger")
    public EventLogger defaultEventLogger(@Autowired @Qualifier("consoleEventLogger") EventLogger consoleEventLogger) {
        return consoleEventLogger;
    }

    @Bean
    @Scope("prototype")
    public Event event(@Autowired DateFormat dateFormat) {
        return new Event(new Date(), dateFormat);
    }

    @Bean
    public DateFormat dateFormat() {
        return DateFormat.getDateTimeInstance(SHORT, SHORT, new Locale("ru"));
    }

    @Bean
    public App app(@Autowired Client client,
                   @Value("#{T(ua.epam.spring.core.beans.Event).isDay() ? consoleEventLogger : fileEventLogger}") EventLogger defaultEventLogger,
                   @Autowired Map<EventType, EventLogger> eventTypeLoggerMap) {
        return new App(client, defaultEventLogger, eventTypeLoggerMap);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource(@Value("${jdbc.driverClassName}") String driverClassName, @Value("${jdbc.url}") String url,
                                 @Value("${jdbc.userName}") String username, @Value("${jdbc.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean("configurer")
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        Resource resource1 = new ClassPathResource("jdbc.properties");
        Resource resource2 = new ClassPathResource("client.properties");
        configurer.setLocations(resource1, resource2);
        return configurer;
    }
}
