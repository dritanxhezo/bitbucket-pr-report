package net.ngeor.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class DateHelperTest {

    @Test
    public void parseUtcDate_shouldParseDate() throws ParseException {
        LocalDate expected = DateHelper.localDate(2016, 5, 5);
        LocalDate actual = DateHelper.parseLocalDate("2016-05-05");
        assertEquals(expected, actual);
    }

    @Test(expected = ParseException.class)
    public void parseUtcDate_withNullInput_shouldThrowParseException() throws ParseException {
        DateHelper.parseLocalDate(null);
    }

    @Test(expected = ParseException.class)
    public void parseUtcDate_withEmptyInput_shouldThrowParseException() throws ParseException {
        DateHelper.parseLocalDate("");
    }

    @Test
    public void utcToday_shouldReturnCurrentDate() {
        LocalDate expected = new DateTime(DateTimeZone.UTC).toLocalDate();
        LocalDate actual = DateHelper.utcToday();
        assertEquals(expected, actual);
    }
}
