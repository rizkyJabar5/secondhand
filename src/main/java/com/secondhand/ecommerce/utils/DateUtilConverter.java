package com.secondhand.ecommerce.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtilConverter {

    public static LocalDateTime toLocalDate(Date dateToConvert) {
        ZoneId zoneId = ZoneId.of("Asia/Jakarta");
        return LocalDateTime.ofInstant(dateToConvert.toInstant(), zoneId);
    }
}
