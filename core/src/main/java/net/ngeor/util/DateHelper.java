package net.ngeor.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import org.apache.commons.lang3.StringUtils;

/**
 * Date helper utility class.
 */
public final class DateHelper {

    private DateHelper() {
    }

    public static LocalDate localDate(int year, int month, int day) {
        return LocalDate.of(year, month, day);
    }

    /**
     * Parses the given date.
     * @param text The date to parse.
     * @return The parsed date.
     * @throws ParseException if the date cannot be parsed.
     */
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
