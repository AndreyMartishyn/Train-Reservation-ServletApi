package ua.martishyn.app.tags;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.martishyn.app.data.tags.DateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatTest {

    @Test
    public void shouldFormatLocalDateTimeToString(){
        String expected = "2022-10-10 12:22";
        LocalDateTime dateTime = LocalDateTime.of(2022, 10, 10, 12, 22);
        String actual = DateFormat.formatLocalDateTime(dateTime, "yyyy-MM-dd HH:mm");
        Assert.assertEquals(expected,actual);
    }
}
