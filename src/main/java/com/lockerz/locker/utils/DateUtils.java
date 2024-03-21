package com.lockerz.locker.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils {

    public static long countDaysFromNow(LocalDateTime startDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        return ChronoUnit.DAYS.between(startDate.toLocalDate(), currentDate.toLocalDate()) + 1;
    }
}
