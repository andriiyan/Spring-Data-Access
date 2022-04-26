package com.github.andriiyan.spring_data_access.impl.utils;

import org.springframework.lang.NonNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtils {
    private static final String UTC_ZONE = "UTC";

    public static final DateTimeFormatter dbDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    @NonNull
    public static Date getDateFromZonedDateTime(@NonNull ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.withZoneSameInstant(ZoneId.of(UTC_ZONE)).toInstant());
    }

    @NonNull
    public static ZonedDateTime fromDate(@NonNull Date date) {
        return ZonedDateTime.now().with(date.toInstant()).withZoneSameInstant(ZoneId.of(UTC_ZONE));
    }

}
