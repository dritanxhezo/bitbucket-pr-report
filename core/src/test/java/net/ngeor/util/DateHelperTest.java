package net.ngeor.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class DateHelperTest {

    @Test
    public void utcDate_shouldCreateDate() {
        DateTime actual = DateHelper.utcDate(2016, 4, 30);
        assertEquals("2016-04-30T00:00:00.000Z", actual.toString());
    }

    @Test
    public void parseUtcDate_shouldParseDate() throws ParseException {
        DateTime expected = DateHelper.utcDate(2016, 5, 5);
        DateTime actual = DateHelper.parseUtcDate("2016-05-05");
        assertEquals(expected, actual);
    }

    @Test(expected = ParseException.class)
    public void parseUtcDate_withNullInput_shouldThrowParseException() throws ParseException {
        DateHelper.parseUtcDate(null);
    }

    @Test(expected = ParseException.class)
    public void parseUtcDate_withEmptyInput_shouldThrowParseException() throws ParseException {
        DateHelper.parseUtcDate("");
    }

    @Test
    public void utcToday_shouldReturnCurrentDate() {
        DateTime expected = new DateTime(DateTimeZone.UTC).withTimeAtStartOfDay();
        DateTime actual = DateHelper.utcToday();
        assertEquals(expected, actual);
    }
}
