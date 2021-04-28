package com.SafetyNet_Alerts.SafetyNetAlert.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}