package net.ngeor.dates;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date date(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);
        return calendar.getTime();
    }
}
