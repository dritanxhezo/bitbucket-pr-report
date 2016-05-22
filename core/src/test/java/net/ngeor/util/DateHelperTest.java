package net.ngeor.util;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class DateHelperTest {
    @Test
    public void date_shouldCreateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 3, 30, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date expected = calendar.getTime();
        Date actual = DateHelper.date(2016, 4, 30);
        assertEquals(expected, actual);
    }

    @Test
    public void utcDate_shouldCreateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(2016, 3, 30, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date expected = calendar.getTime();
        Date actual = DateHelper.utcDate(2016, 4, 30);
        assertEquals(expected, actual);
    }

    @Test
    public void parseUtcDate_shouldParseDate() throws ParseException {
        Date expected = DateHelper.utcDate(2016, 5, 5);
        Date actual = DateHelper.parseUtcDate("2016-05-05");
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date expected = calendar.getTime();
        Date actual = DateHelper.utcToday();
        assertEquals(expected, actual);
    }
}
