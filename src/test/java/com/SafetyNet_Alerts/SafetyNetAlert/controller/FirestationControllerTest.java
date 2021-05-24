package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.jsoniter.JsonIterator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() throws IOException {
        String jsonAsString = "{\"persons\":[{\"firstName\":\"Khalil\",\"lastName\":\"Sleaby\",\"address\":\"1234 Street St\",\"city\":\"city\",\"zip\":\"12345\",\"phone\":\"123-456-7890\",\"email\":\"khalil@email.com\"},{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tenz@email.com\"},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6544\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"address\":\"29 15th St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"},{\"firstName\":\"Tessa\",\"lastName\":\"Carman\",\"address\":\"834 Binoc Ave\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tenz@email.com\"},{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"address\":\"644 Gershwin Cir\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Foster\",\"lastName\":\"Shepard\",\"address\":\"748 Townings Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6544\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6874\",\"email\":\"tcoop@ymail.com\"},{\"firstName\":\"Lily\",\"lastName\":\"Cooper\",\"address\":\"489 Manchester St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-9845\",\"email\":\"lily@email.com\"},{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7878\",\"email\":\"soph@email.com\"},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7512\",\"email\":\"ward@email.com\"},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7512\",\"email\":\"zarc@email.com\"},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-8547\",\"email\":\"reg@email.com\"},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7462\",\"email\":\"jpeter@email.com\"},{\"firstName\":\"Ron\",\"lastName\":\"Peters\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-8888\",\"email\":\"jpeter@email.com\"},{\"firstName\":\"Allison\",\"lastName\":\"Boyd\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-9888\",\"email\":\"aly@imail.com\"},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"bstel@email.com\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"ssanw@email.com\"},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"bstel@email.com\"},{\"firstName\":\"Clive\",\"lastName\":\"Ferguson\",\"address\":\"748 Townings Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6741\",\"email\":\"clivfd@ymail.com\"},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"address\":\"951 LoneTree Rd\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7458\",\"email\":\"gramps@email.com\"}],\n" +
                "  \"firestations\":[{\"address\":\"1234 Street St\",\"station\":\"1\"},{\"address\":\"1509 Culver St\",\"station\":\"3\"},{\"address\":\"29 15th St\",\"station\":\"2\"},{\"address\":\"834 Binoc Ave\",\"station\":\"3\"},{\"address\":\"644 Gershwin Cir\",\"station\":\"1\"},{\"address\":\"748 Townings Dr\",\"station\":\"3\"},{\"address\":\"112 Steppes Pl\",\"station\":\"3\"},{\"address\":\"489 Manchester St\",\"station\":\"4\"},{\"address\":\"892 Downing Ct\",\"station\":\"2\"},{\"address\":\"908 73rd St\",\"station\":\"1\"},{\"address\":\"112 Steppes Pl\",\"station\":\"4\"},{\"address\":\"947 E. Rose Dr\",\"station\":\"1\"},{\"address\":\"748 Townings Dr\",\"station\":\"3\"},{\"address\":\"951 LoneTree Rd\",\"station\":\"2\"}],\n" +
                "  \"medicalrecords\":[{\"firstName\":\"Khalil\",\"lastName\":\"Sleaby\",\"birthdate\":\"01/01/1981\",\"medications\":[\"firstMed:350mg\",\"secondMed:100mg\"],\"allergies\":[\"firstAllergy\",\"secondAllergy\"]},{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1984\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1989\",\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"birthdate\":\"02/18/2012\",\"medications\":[],\"allergies\":[\"peanut\"]},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"birthdate\":\"09/06/2017\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"birthdate\":\"01/08/1986\",\"medications\":[\"tetracyclaz:650mg\"],\"allergies\":[\"xilliathal\"]},{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"birthdate\":\"01/03/1989\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Tessa\",\"lastName\":\"Carman\",\"birthdate\":\"02/18/2012\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"birthdate\":\"09/06/2000\",\"medications\":[],\"allergies\":[\"shellfish\"]},{\"firstName\":\"Foster\",\"lastName\":\"Shepard\",\"birthdate\":\"01/08/1980\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"birthdate\":\"03/06/1994\",\"medications\":[\"hydrapermazol:300mg\",\"dodoxadin:30mg\"],\"allergies\":[\"shellfish\"]},{\"firstName\":\"Lily\",\"lastName\":\"Cooper\",\"birthdate\":\"03/06/1994\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/1988\",\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"]},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/1985\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/2017\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"birthdate\":\"08/30/1979\",\"medications\":[\"thradox:700mg\"],\"allergies\":[\"illisoxian\"]},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"birthdate\":\"03/06/1982\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Ron\",\"lastName\":\"Peters\",\"birthdate\":\"04/06/1965\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Allison\",\"lastName\":\"Boyd\",\"birthdate\":\"03/15/1965\",\"medications\":[\"aznol:200mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"birthdate\":\"12/06/1975\",\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"birthdate\":\"07/08/1980\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"birthdate\":\"03/06/2014\",\"medications\":[\"noxidian:100mg\",\"pharmacol:2500mg\"],\"allergies\":[]},{\"firstName\":\"Clive\",\"lastName\":\"Ferguson\",\"birthdate\":\"03/06/1994\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"birthdate\":\"08/06/1945\",\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}]}";
        Path path = Paths.get(new ClassPathResource("data.json").getURI());
        System.out.println(path.toString());
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Files.writeString(path, jsonAsString);

        FirestationDaoImpl.firestationList = JsonIterator.deserialize(Files.readString(path), JsonFileModel.class)
                .getFirestations();
        MedicalRecordDaoImpl.medicalRecordList = JsonIterator.deserialize(Files.readString(path), JsonFileModel.class)
                .getMedicalrecords();
        PersonDaoImpl.personList = JsonIterator.deserialize(Files.readString(path), JsonFileModel.class)
                .getPersons();
    }

    @Test
    void getAllFirestations() throws Exception {

        mockMvc.perform(get("/firestations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1234 Street St")));
    }

    @Test
    void addFirestation() throws Exception {
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\": \"4321 Street St\",\"station\": \"1\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("4321 Street St"))
                .andExpect(jsonPath("$.station").value("1"));
    }

    @Test
    void updateFirestation() throws Exception {
        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "1234 Street St")
                .content("{\"address\": \"1234 Street St\",\"station\": \"2\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("1234 Street St"))
                .andExpect(jsonPath("$.station").value("2"));
    }

    @Test
    void deleteFirestation() throws Exception {
        mockMvc.perform(delete("/firestation")
                .param("address", "1234 Street St")
                .param("station", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getListOfPersonWithAgeCat() throws Exception {

        mockMvc.perform(get("/firestation")
                .param("station_number", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Khalil")));
    }

    @Test
    void getListOfPersonWithAgeCat_whenListIsEmpty_returnEmptyJson() throws Exception {
        //given
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\": \"No Address corresponding\",\"station\": \"5\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("No Address corresponding"))
                .andExpect(jsonPath("$.station").value("5"));
        //When and then
        mockMvc.perform(get("/firestation")
                .param("station_number", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{}")));
    }

    @Test
    void getPeopleListServedByFirestationByAddress() throws Exception {

        mockMvc.perform(get("/fire")
                .param("address", "1234 Street St"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"firestationNumber\":\"1\",\"peopleWithLastNamePhoneAgesList\":[{\"lastName\":\"Sleaby\",\"phone\":\"123-456-7890\",\"age\":40,\"medications\":[\"firstMed:350mg\",\"secondMed:100mg\"],\"allergies\":[\"firstAllergy\",\"secondAllergy\"]}]}"))
                .andExpect(content().string(containsString("Sleaby")));
    }

    @Test
    void getPeopleListServedByFirestationByAddress_whenNoPeopleFound_returnEmptyJson() throws Exception {
        //given
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\": \"No Address corresponding\",\"station\": \"5\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("No Address corresponding"))
                .andExpect(jsonPath("$.station").value("5"));
        //when and then
        mockMvc.perform(get("/fire")
                .param("address", "No Address corresponding"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{}")));
    }

    @Test
    void getHouseDto() throws Exception {
        mockMvc.perform(get("/flood/stations")
                .param("stations", "1")
                .param("stations", "2"))
                .andDo(print())
                .andExpect(content().string(containsString("Sleaby")))
                .andExpect(content().string(containsString("Marrack")))
                .andExpect(status().isOk());
    }

    @Test
    void getHouseDto_WhenNoHousFound_ReturnEmptyJson() throws Exception {
        //given
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\": \"No Address corresponding\",\"station\": \"5\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("No Address corresponding"))
                .andExpect(jsonPath("$.station").value("5"));
        //when and then
        mockMvc.perform(get("/flood/stations")
                .param("stations", "5"))
                .andDo(print())
                .andExpect(content().string(containsString("{}")))
                .andExpect(status().isOk());
    }

    @AfterAll
    static void afterAll() throws IOException {
        String jsonAsString = "{\"persons\":[{\"firstName\":\"Khalil\",\"lastName\":\"Sleaby\",\"address\":\"1234 Street St\",\"city\":\"city\",\"zip\":\"12345\",\"phone\":\"123-456-7890\",\"email\":\"khalil@email.com\"},{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tenz@email.com\"},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"address\":\"1509 Culver St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6544\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"address\":\"29 15th St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6513\",\"email\":\"drk@email.com\"},{\"firstName\":\"Tessa\",\"lastName\":\"Carman\",\"address\":\"834 Binoc Ave\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"tenz@email.com\"},{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"address\":\"644 Gershwin Cir\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Foster\",\"lastName\":\"Shepard\",\"address\":\"748 Townings Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6544\",\"email\":\"jaboyd@email.com\"},{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6874\",\"email\":\"tcoop@ymail.com\"},{\"firstName\":\"Lily\",\"lastName\":\"Cooper\",\"address\":\"489 Manchester St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-9845\",\"email\":\"lily@email.com\"},{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7878\",\"email\":\"soph@email.com\"},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7512\",\"email\":\"ward@email.com\"},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"address\":\"892 Downing Ct\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7512\",\"email\":\"zarc@email.com\"},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-8547\",\"email\":\"reg@email.com\"},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"address\":\"908 73rd St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7462\",\"email\":\"jpeter@email.com\"},{\"firstName\":\"Ron\",\"lastName\":\"Peters\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-8888\",\"email\":\"jpeter@email.com\"},{\"firstName\":\"Allison\",\"lastName\":\"Boyd\",\"address\":\"112 Steppes Pl\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-9888\",\"email\":\"aly@imail.com\"},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"bstel@email.com\"},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"ssanw@email.com\"},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"address\":\"947 E. Rose Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7784\",\"email\":\"bstel@email.com\"},{\"firstName\":\"Clive\",\"lastName\":\"Ferguson\",\"address\":\"748 Townings Dr\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6741\",\"email\":\"clivfd@ymail.com\"},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"address\":\"951 LoneTree Rd\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-7458\",\"email\":\"gramps@email.com\"}],\n" +
                "  \"firestations\":[{\"address\":\"1234 Street St\",\"station\":\"1\"},{\"address\":\"1509 Culver St\",\"station\":\"3\"},{\"address\":\"29 15th St\",\"station\":\"2\"},{\"address\":\"834 Binoc Ave\",\"station\":\"3\"},{\"address\":\"644 Gershwin Cir\",\"station\":\"1\"},{\"address\":\"748 Townings Dr\",\"station\":\"3\"},{\"address\":\"112 Steppes Pl\",\"station\":\"3\"},{\"address\":\"489 Manchester St\",\"station\":\"4\"},{\"address\":\"892 Downing Ct\",\"station\":\"2\"},{\"address\":\"908 73rd St\",\"station\":\"1\"},{\"address\":\"112 Steppes Pl\",\"station\":\"4\"},{\"address\":\"947 E. Rose Dr\",\"station\":\"1\"},{\"address\":\"748 Townings Dr\",\"station\":\"3\"},{\"address\":\"951 LoneTree Rd\",\"station\":\"2\"}],\n" +
                "  \"medicalrecords\":[{\"firstName\":\"Khalil\",\"lastName\":\"Sleaby\",\"birthdate\":\"01/01/1981\",\"medications\":[\"firstMed:350mg\",\"secondMed:100mg\"],\"allergies\":[\"firstAllergy\",\"secondAllergy\"]},{\"firstName\":\"John\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1984\",\"medications\":[\"aznol:350mg\",\"hydrapermazol:100mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"birthdate\":\"03/06/1989\",\"medications\":[\"pharmacol:5000mg\",\"terazine:10mg\",\"noznazol:250mg\"],\"allergies\":[]},{\"firstName\":\"Tenley\",\"lastName\":\"Boyd\",\"birthdate\":\"02/18/2012\",\"medications\":[],\"allergies\":[\"peanut\"]},{\"firstName\":\"Roger\",\"lastName\":\"Boyd\",\"birthdate\":\"09/06/2017\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Felicia\",\"lastName\":\"Boyd\",\"birthdate\":\"01/08/1986\",\"medications\":[\"tetracyclaz:650mg\"],\"allergies\":[\"xilliathal\"]},{\"firstName\":\"Jonanathan\",\"lastName\":\"Marrack\",\"birthdate\":\"01/03/1989\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Tessa\",\"lastName\":\"Carman\",\"birthdate\":\"02/18/2012\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Peter\",\"lastName\":\"Duncan\",\"birthdate\":\"09/06/2000\",\"medications\":[],\"allergies\":[\"shellfish\"]},{\"firstName\":\"Foster\",\"lastName\":\"Shepard\",\"birthdate\":\"01/08/1980\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Tony\",\"lastName\":\"Cooper\",\"birthdate\":\"03/06/1994\",\"medications\":[\"hydrapermazol:300mg\",\"dodoxadin:30mg\"],\"allergies\":[\"shellfish\"]},{\"firstName\":\"Lily\",\"lastName\":\"Cooper\",\"birthdate\":\"03/06/1994\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Sophia\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/1988\",\"medications\":[\"aznol:60mg\",\"hydrapermazol:900mg\",\"pharmacol:5000mg\",\"terazine:500mg\"],\"allergies\":[\"peanut\",\"shellfish\",\"aznol\"]},{\"firstName\":\"Warren\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/1985\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Zach\",\"lastName\":\"Zemicks\",\"birthdate\":\"03/06/2017\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Reginold\",\"lastName\":\"Walker\",\"birthdate\":\"08/30/1979\",\"medications\":[\"thradox:700mg\"],\"allergies\":[\"illisoxian\"]},{\"firstName\":\"Jamie\",\"lastName\":\"Peters\",\"birthdate\":\"03/06/1982\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Ron\",\"lastName\":\"Peters\",\"birthdate\":\"04/06/1965\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Allison\",\"lastName\":\"Boyd\",\"birthdate\":\"03/15/1965\",\"medications\":[\"aznol:200mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Brian\",\"lastName\":\"Stelzer\",\"birthdate\":\"12/06/1975\",\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]},{\"firstName\":\"Shawna\",\"lastName\":\"Stelzer\",\"birthdate\":\"07/08/1980\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Kendrik\",\"lastName\":\"Stelzer\",\"birthdate\":\"03/06/2014\",\"medications\":[\"noxidian:100mg\",\"pharmacol:2500mg\"],\"allergies\":[]},{\"firstName\":\"Clive\",\"lastName\":\"Ferguson\",\"birthdate\":\"03/06/1994\",\"medications\":[],\"allergies\":[]},{\"firstName\":\"Eric\",\"lastName\":\"Cadigan\",\"birthdate\":\"08/06/1945\",\"medications\":[\"tradoxidine:400mg\"],\"allergies\":[]}]}";
        Path path = Paths.get(new ClassPathResource("data.json").getURI());
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        Files.writeString(path, jsonAsString);

        FirestationDaoImpl.firestationList = JsonIterator.deserialize(Files.readString(path), JsonFileModel.class)
                .getFirestations();
        MedicalRecordDaoImpl.medicalRecordList = JsonIterator.deserialize(Files.readString(path), JsonFileModel.class)
                .getMedicalrecords();
        PersonDaoImpl.personList = JsonIterator.deserialize(Files.readString(path), JsonFileModel.class)
                .getPersons();
    }
}
