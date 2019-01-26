package net.ngeor.util;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link LocalDateInterval}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
class LocalDateIntervalTest {
    @Test
    void shouldCreateLocalDateInterval() {
        LocalDate start                     = LocalDate.of(2016, 6, 10);
        LocalDate end                       = LocalDate.of(2016, 7, 10);
        LocalDateInterval localDateInterval = new LocalDateInterval(start, end);
        assertEquals(start, localDateInterval.getStart());
        assertEquals(end, localDateInterval.getEnd());
    }
}
