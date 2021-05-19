package com.SafetyNet_Alerts.SafetyNetAlert.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateHelper {
    /**
     * date time format uses in Api
     */
    public static String DATE_TIME_FORMAT = "MM/dd/yyyy";
    /**
     * Base age of adults
     */
    public static int adultAge = 18;

    /**
     * Check if person is adult or child
     *
     * @param birthDateAsString
     * @return true if person is adult
     */
    public static boolean isAdult(String birthDateAsString) {
        LocalDate birthDate = LocalDate.parse(birthDateAsString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        long diff = Math.abs(ChronoUnit.YEARS.between(LocalDate.now(), birthDate));

        return diff >= adultAge;
    }

    /**
     * Calculate the age of person by hes birthday
     *
     * @param birthDateAsString Birthday as string of MM/dd/yyyy date time format
     * @return age of person as integer
     */
    public static int calculateAge(String birthDateAsString) {
        LocalDate birthDate = LocalDate.parse(birthDateAsString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        long diff = Math.abs(ChronoUnit.YEARS.between(LocalDate.now(), birthDate));
        return (int) diff;
    }
}
