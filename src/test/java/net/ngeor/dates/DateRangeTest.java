package net.ngeor.dates;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateRangeTest {
    @Test
    public void shouldCreateWithFromAndUntil() {
        Date from = DateHelper.utcDate(2016, 5, 4);
        Date until = DateHelper.utcDate(2016, 5, 5);

        // act
        DateRange dateRange = new DateRange(from, until);

        // assert
        assertEquals(from, dateRange.getFrom());
        assertEquals(until, dateRange.getUntil());
    }
}
