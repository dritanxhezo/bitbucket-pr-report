package net.ngeor.util;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.util.Date;

public class DateHelper {

    @NotNull
    public static LocalDate localDate(int year, int month, int day) {
        return new LocalDate(year, month, day);
    }

    @NotNull
    public static Date utcDate(int year, int month, int day) {
        return DateHelper.localDate(year, month, day).toDateTimeAtStartOfDay(DateTimeZone.UTC).toDate();
    }

    public static LocalDate parseLocalDate(String text) throws ParseException {
        if (StringUtils.isBlank(text)) {
            throw new ParseException("Cannot parse null text", 0);
        }

        return LocalDate.parse(text);
    }

    public static LocalDate utcToday() {
        return new LocalDate();
    }

}
