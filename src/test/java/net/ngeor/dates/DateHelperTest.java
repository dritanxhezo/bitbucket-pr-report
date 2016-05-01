package net.ngeor.dates;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class DateHelperTest {
    @Test
    public void shouldCreateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 3, 30, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date expected = calendar.getTime();
        Date actual = DateHelper.date(2016, 4, 30);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldCreateUtcDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(2016, 3, 30, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date expected = calendar.getTime();
        Date actual = DateHelper.utcDate(2016, 4, 30);
        assertEquals(expected, actual);
    }
}
