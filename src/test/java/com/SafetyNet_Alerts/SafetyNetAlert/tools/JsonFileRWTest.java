package com.SafetyNet_Alerts.SafetyNetAlert.tools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonFileRWTest {
    JsonFileRW jsonFileRW;

    @Test
    void jsonFileToString() {
        jsonFileRW = new JsonFileRW();
        String result = jsonFileRW.jsonFileToString();
        assertThat(result).isNotEqualTo("");

    }
}