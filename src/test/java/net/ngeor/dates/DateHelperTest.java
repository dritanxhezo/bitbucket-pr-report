package net.ngeor.dates;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateHelperTest {
    @Test
    public void shouldCreateDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 3, 30, 0, 0, 0);
        Date expected = calendar.getTime();
        Date actual = DateHelper.date(2016, 4, 30);
        assertEquals(expected, actual);
    }
}
