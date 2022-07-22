package com.secondhand.ecommerce.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static com.secondhand.ecommerce.utils.SecondHandConst.TIMESTAMP;

public class DateUtilConverter {

    public static DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern(TIMESTAMP).withLocale(Locale.getDefault());
    }

    public static LocalDateTime toLocalDate(Date dateToConvert) {
        return LocalDateTime.ofInstant(dateToConvert.toInstant(), (ZoneId) ZoneId.getAvailableZoneIds());
    }
}
