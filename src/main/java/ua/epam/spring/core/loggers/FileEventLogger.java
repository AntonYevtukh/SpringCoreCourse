package ua.epam.spring.core.loggers;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.epam.spring.core.beans.Event;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component("fileEventLogger")
public class FileEventLogger implements EventLogger {

    private String fileName;
    private File file;

    public FileEventLogger(@Value("D:\\Work\\Java\\Projects\\SpringCoreCourse\\log.log") String fileName) {
        this.fileName = fileName;
    }

    @PostConstruct
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
