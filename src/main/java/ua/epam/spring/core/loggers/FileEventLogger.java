package ua.epam.spring.core.loggers;

import org.apache.commons.io.FileUtils;
import ua.epam.spring.core.beans.Event;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    private String fileName;
    private File file;

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    public void init() throws IOException {
        this.file = new File(fileName);
        if (!file.canWrite())
            throw new IOException("Can\'t write to file " + fileName);
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.toString() + "\n", true);
        } catch (IOException e) {

        }
    }
}
