package ua.martishyn.app.data.tags;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormat {
    private DateFormat() {
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
