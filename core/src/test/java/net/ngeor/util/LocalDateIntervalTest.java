package net.ngeor.util;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalDateIntervalTest {
    @Test
    public void shouldCreateLocalDateInterval() {
        LocalDate start = new LocalDate(2016, 6, 10);
        LocalDate end = new LocalDate(2016, 7, 10);
        LocalDateInterval localDateInterval = new LocalDateInterval(start, end);
        assertEquals(start, localDateInterval.getStart());
        assertEquals(end, localDateInterval.getEnd());
    }
}
