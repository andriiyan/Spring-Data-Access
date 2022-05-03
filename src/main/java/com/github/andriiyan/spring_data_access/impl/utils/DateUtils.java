package com.github.andriiyan.spring_data_access.impl.utils;

import org.springframework.lang.NonNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {
    private static final String UTC_ZONE = "UTC";

    public static final DateTimeFormatter dbDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final Calendar calendar = Calendar.getInstance();

    @NonNull
    public static Date toUTCDate(@NonNull Date date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.from(date.toInstant().atZone(ZoneId.of(UTC_ZONE)));
        calendar.set(Calendar.YEAR, zonedDateTime.getYear());
        calendar.set(Calendar.DAY_OF_MONTH, zonedDateTime.getDayOfMonth());
        calendar.set(Calendar.MONTH, zonedDateTime.getMonthValue() - 1);
        calendar.set(Calendar.HOUR_OF_DAY, zonedDateTime.getHour());
        calendar.set(Calendar.MINUTE, zonedDateTime.getMinute());
        calendar.set(Calendar.SECOND, zonedDateTime.getSecond());
        return calendar.getTime();
    }

}
