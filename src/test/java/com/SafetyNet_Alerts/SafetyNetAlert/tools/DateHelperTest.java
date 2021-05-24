package com.SafetyNet_Alerts.SafetyNetAlert.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateHelperTest {
    DateHelper dateHelperUnderTest;

    @BeforeEach
    void setUp() {
        dateHelperUnderTest = new DateHelper();
    }

    @Test
    void isAdult() {
        boolean result;
        result = dateHelperUnderTest.isAdult("01/01/1980");
        assertTrue(result);
    }

    @Test
    void calculateAge() {
        String birthDate = LocalDate.now().minusYears(3L).format(DateTimeFormatter.ofPattern(DateHelper.DATE_TIME_FORMAT_FOR_CALCULATING_AGE));
        assertThat(DateHelper.calculateAge(birthDate)).isEqualTo(3);
    }
}