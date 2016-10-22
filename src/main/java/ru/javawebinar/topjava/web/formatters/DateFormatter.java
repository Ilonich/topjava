package ru.javawebinar.topjava.web.formatters;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Created by Никола on 22.10.2016.
 */
public class DateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return TimeUtil.parseLocalDate(text);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.toString();
    }
}
