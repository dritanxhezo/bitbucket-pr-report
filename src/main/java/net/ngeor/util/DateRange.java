package net.ngeor.util;

import java.util.Date;

public class DateRange {
    private final Date from;
    private final Date until;

    public DateRange(Date from, Date until) {
        this.from = from;
        this.until = until;
    }

    public Date getFrom() {
        return from;
    }

    public Date getUntil() {
        return until;
    }
}
