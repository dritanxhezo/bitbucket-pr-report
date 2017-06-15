package net.ngeor.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class LocalDateIntervalTest {
    @Test
    public void shouldCreateLocalDateInterval() {
        LocalDate start = LocalDate.of(2016, 6, 10);
        LocalDate end = LocalDate.of(2016, 7, 10);
        LocalDateInterval localDateInterval = new LocalDateInterval(start, end);
        assertEquals(start, localDateInterval.getStart());
        assertEquals(end, localDateInterval.getEnd());
    }
}
