package net.ngeor.util;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import java.text.ParseException;

public class DateHelper {

    @NotNull
    public static DateTime utcDate(int year, int month, int day) {
        return new DateTime(year, month, day, 0, 0, DateTimeZone.UTC);
    }

    public static DateTime parseUtcDate(String text) throws ParseException {
        if (StringUtils.isBlank(text)) {
            throw new ParseException("Cannot parse null text", 0);
        }

        return LocalDate.parse(text).toDateTimeAtStartOfDay(DateTimeZone.UTC);
    }

    public static DateTime utcToday() {
        return new DateTime(DateTimeZone.UTC).withTimeAtStartOfDay();
    }

}
