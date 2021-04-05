package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllPersons() throws Exception {

        mockMvc.perform(get("/Persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Khalil")));

    }

    @Test
    void getAllFirestations() throws Exception {

        mockMvc.perform(get("/Firestations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1509 Culver St")));

    }

    @Test
    void getAllMedicalRecordss() throws Exception {

        mockMvc.perform(get("/MedicalRecords"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("pharmacol:5000mg")));

    }
}