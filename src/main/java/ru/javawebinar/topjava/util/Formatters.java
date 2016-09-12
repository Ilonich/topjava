package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Никола on 11.09.2016.
 */
public class Formatters {
    private Formatters() {
    }
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MMM/uuuu@HH:mm.", Locale.ENGLISH);

    public static String formatDateTime(LocalDateTime dateTime){
        return dateTimeFormatter.format(dateTime);
    }

    public static LocalDateTime parse(String date){
        return LocalDateTime.from(dateTimeFormatter.parse(date));
    }
}
