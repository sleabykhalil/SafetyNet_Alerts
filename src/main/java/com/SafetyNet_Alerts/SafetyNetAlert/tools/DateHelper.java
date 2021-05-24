package com.SafetyNet_Alerts.SafetyNetAlert.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateHelper {
    /**
     * date time format uses in Api
     */
    public static String DATE_TIME_FORMAT_FOR_CALCULATING_AGE = "MM/dd/yyyy";
    public static String DATE_TIME_FORMAT_FOR_FILE_NAMING = "dd_MMM_yyyy_HH_mm_ss_S";

    /**
     * Base age of adults
     */
    public static int adultAge = 18;

    /**
     * Check if person is adult or child
     *
     * @param birthDateAsString person birthday
     * @return true if person is adult
     */
    public static boolean isAdult(String birthDateAsString) {
        LocalDate birthDate = LocalDate.parse(birthDateAsString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_FOR_CALCULATING_AGE));
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
        LocalDate birthDate = LocalDate.parse(birthDateAsString, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_FOR_CALCULATING_AGE));
        long diff = Math.abs(ChronoUnit.YEARS.between(LocalDate.now(), birthDate));
        return (int) diff;
    }
}
