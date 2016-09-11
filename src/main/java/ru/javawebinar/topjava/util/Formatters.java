package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Никола on 11.09.2016.
 */
public class Formatters {
    private Formatters() {
    }
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm.");

    public static String formatDateTime(LocalDateTime dateTime){
        return dateTimeFormatter.format(dateTime);
    }
}
