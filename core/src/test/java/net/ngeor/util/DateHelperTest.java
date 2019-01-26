package net.ngeor.util;

import java.text.ParseException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link DateHelper}.
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class DateHelperTest {
    @Test
    public void parseUtcDate_shouldParseDate() throws ParseException {
        LocalDate expected = DateHelper.localDate(2016, 5, 5);
        LocalDate actual   = DateHelper.parseLocalDate("2016-05-05");
        assertEquals(expected, actual);
    }

    @Test
    public void parseUtcDate_withNullInput_shouldThrowParseException() throws ParseException {
        assertThatThrownBy(() -> DateHelper.parseLocalDate(null)).isInstanceOf(ParseException.class);
    }

    @Test
    public void parseUtcDate_withEmptyInput_shouldThrowParseException() throws ParseException {
        assertThatThrownBy(() -> DateHelper.parseLocalDate("")).isInstanceOf(ParseException.class);
    }

    @Test
    public void utcToday_shouldReturnCurrentDate() {
        LocalDate expected = LocalDate.now();
        LocalDate actual   = DateHelper.utcToday();
        assertEquals(expected, actual);
    }
}
