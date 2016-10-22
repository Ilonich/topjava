package ru.javawebinar.topjava.web.formatters;

import org.springframework.format.Formatter;
import ru.javawebinar.topjava.util.TimeUtil;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

/**
 * Created by Никола on 22.10.2016.
 */
public class TimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return TimeUtil.parseLocalTime(text);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.toString();
    }
}
