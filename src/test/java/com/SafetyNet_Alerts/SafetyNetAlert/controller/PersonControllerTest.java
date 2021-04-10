package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

    /**
     * Add new person
     *
     * @throws Exception
     */
    @Test
    void addPerson() throws Exception {
        mockMvc.perform(post("/Person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(" { " + "      \"firstName\": \"Khalil\",\n" +
                        "      \"lastName\": \"Boyd\",\n" +
                        "      \"address\": \"1509 Culver St\",\n" +
                        "      \"city\": \"Culver\",\n" +
                        "      \"zip\": \"97451\",\n" +
                        "      \"phone\": \"841-874-6512\",\n" +
                        "      \"email\": \"jaboyd@email.com\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Khalil"))
                .andExpect(jsonPath("$.lastName").value("Boyd"))
        //TODO add missing values
        ;
    }

    @Test
    void updatePerson() throws Exception {
        mockMvc.perform(put("/Person")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstName", "Khalil")
                .param("lastName", "Boyd")
                .content(" { " + "      \"firstName\": \"Khalil\",\n" +
                        "      \"lastName\": \"Boyd\",\n" +
                        "      \"address\": \"new address\",\n" +
                        "      \"city\": \"new city\",\n" +
                        "      \"zip\": \"12345\",\n" +
                        "      \"phone\": \"123-456-7890\",\n" +
                        "      \"email\": \"khalil@email.com\"\n" +
                        "    }")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Khalil"))
                .andExpect(jsonPath("$.lastName").value("Boyd"))
                .andExpect(jsonPath("$.address").value("new address"))
                .andExpect(jsonPath("$.city").value("new city"))
        //TODO add missing values
        ;
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(delete("/Person")
                .param("firstName", "Khalil")
                .param("lastName", "Boyd"))
                .andDo(print())
                .andExpect(status().isOk())

        //TODO add missing values
        ;
    }

}