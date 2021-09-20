package ddd.sharedkernel.domain.helpers;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateCustom {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime getDateNow() {
        return LocalDateTime.now(ZoneId.of("UTC"));
    }

    public static String getDateNowString() {
        return getDateNow().format(formatter);
    }

    public static String getDateString(LocalDateTime z) {
        return z.format(formatter);
    }

    public static LocalDateTime getLocalDateTimeFromDateTimeString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        return dateTime;
    }

    public static LocalDateTime getLocalDateTimeFromDateString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        LocalDateTime dateTime = LocalDateTime.parse(dateString + " 00:00:00 UTC", formatter);
        return dateTime;
    }

    public static LocalDateTime getLocalDateTimeFromDateStringDateDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        LocalDateTime dateTime = LocalDateTime.parse(dateString + " 00:00:00 UTC", formatter);
        return dateTime;
    }

}
