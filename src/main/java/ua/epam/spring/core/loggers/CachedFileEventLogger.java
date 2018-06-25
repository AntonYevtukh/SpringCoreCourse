package ua.epam.spring.core.loggers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.epam.spring.core.beans.Event;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component("cachedFileEventLogger")
public class CachedFileEventLogger extends FileEventLogger {

    private int cacheSize;
    private List<Event> cache = new ArrayList<>();

    public CachedFileEventLogger(@Value("D:\\Work\\Java\\Projects\\SpringCoreCourse\\log.log") String fileName, @Value("10") int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);
        if (cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    @PreDestroy
    public void destroy() {
        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }

    private void writeEventsFromCache() {
        cache.forEach((Event event) -> super.logEvent(event));
    }
}
