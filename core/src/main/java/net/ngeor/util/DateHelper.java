package net.ngeor.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateHelper {

    public static LocalDate localDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    public static Date utcDate(int year, int month, int day) {
        return Date.from(DateHelper.localDate(year, month, day).atStartOfDay().toInstant(ZoneOffset.UTC));
    }

    public static LocalDate parseLocalDate(String text) throws ParseException {
        if (StringUtils.isBlank(text)) {
            throw new ParseException("Cannot parse null text", 0);
        }

        return LocalDate.parse(text);
    }

    public static LocalDate utcToday() {
        return LocalDate.now(ZoneId.systemDefault());
    }

}
