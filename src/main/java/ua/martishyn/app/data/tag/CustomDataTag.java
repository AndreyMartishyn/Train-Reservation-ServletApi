package ua.martishyn.app.data.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class CustomDataTag extends SimpleTagSupport {
    @Override
    public void doTag() throws IOException {
        LocalDateTime localNow = LocalDateTime.now(TimeZone.getTimeZone("Europe").toZoneId());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm a");
        String formatDateTime = localNow.format(format);
        JspWriter out = getJspContext().getOut();
        out.println(formatDateTime);
    }
}
