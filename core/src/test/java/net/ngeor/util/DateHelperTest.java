package net.ngeor.util;

import org.junit.Test;
import org.mockito.cglib.core.Local;

import java.text.ParseException;
import java.time.LocalDate;

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
        LocalDate expected = LocalDate.now();
        LocalDate actual = DateHelper.utcToday();
        assertEquals(expected, actual);
    }
}
