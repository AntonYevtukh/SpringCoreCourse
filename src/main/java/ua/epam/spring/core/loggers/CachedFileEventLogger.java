package ua.epam.spring.core.loggers;

import ua.epam.spring.core.beans.Event;

import java.util.ArrayList;
import java.util.List;

public class CachedFileEventLogger extends FileEventLogger {

    private int cacheSize;
    private List<Event> cache = new ArrayList<>();

    public CachedFileEventLogger(String fileName, int cacheSize) {
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

    private void writeEventsFromCache() {
        cache.forEach((Event event) -> super.logEvent(event));
    }

    public void destroy() {
        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }
}
