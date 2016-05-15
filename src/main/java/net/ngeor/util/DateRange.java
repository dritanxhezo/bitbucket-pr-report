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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateRange dateRange = (DateRange) o;

        if (from != null ? !from.equals(dateRange.from) : dateRange.from != null) return false;
        return until != null ? until.equals(dateRange.until) : dateRange.until == null;

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (until != null ? until.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "from=" + from +
                ", until=" + until +
                '}';
    }
}
