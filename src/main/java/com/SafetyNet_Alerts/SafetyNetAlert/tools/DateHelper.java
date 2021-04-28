package com.SafetyNet_Alerts.SafetyNetAlert.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateHelper {
    private static String DATE_TIME_FORMAT = "MM/dd/yyyy";

    public static boolean isAdult(String birthDateAsString) {
        LocalDate birthDate = LocalDate.parse(birthDateAsString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        long diff = Math.abs(ChronoUnit.YEARS.between(LocalDate.now(), birthDate));

        return diff >= 18;
    }
}
