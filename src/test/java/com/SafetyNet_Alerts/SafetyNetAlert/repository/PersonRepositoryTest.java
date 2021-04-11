package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {
    @Autowired
    PersonRepository personRepositoryUnderTest;

    @Mock
    Services servicesMock;

    @Mock
    JsonFileRW jsonFileRWMock;

    JsonFileModel jsonFileModel;
    Person personToTest;
    List<Person> personList;


    @BeforeEach
    void setUp() {
        personToTest = Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("1509 Culver St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();
        lenient().doNothing().when(servicesMock).saveToJsonFile();

        jsonFileModel = new JsonFileModel();
        personList = new ArrayList<>();
        personList.add(personToTest);
        jsonFileModel.setPersons(personList);
        when(jsonFileRWMock.jsonFileToString(anyString())).thenReturn("data");
        when(jsonFileRWMock.jsonAsStringToJsonFileModel("data")).thenReturn(jsonFileModel);

        personRepositoryUnderTest = new PersonRepository(servicesMock, jsonFileRWMock);

    }

    @Test
    void findAll() {
        List<Person> result;
        result = personRepositoryUnderTest.findAll();
        assertThat(result.toString()).contains("Khalil");
    }

    @Test
    void savePerson_whenPersonPassed_AddToJsonFile() {

        Person result = personRepositoryUnderTest.savePerson(personToTest);
        assertThat(result).isEqualTo(personToTest);
    }

    @Test
    void updatePerson_whenTowPersonsPassed_AddPersonAfterAndRemovePersonBeforeToJsonFile() {
        Person personBefore = personToTest;
        Person personAfter = Person.builder()
                .firstName("Khalil")
                .lastName("Boyd")
                .address("1509 Culver St")//filed to be changed
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();

        Person result = personRepositoryUnderTest.updatePerson(personBefore, personAfter);

        assertThat(result).isEqualTo(personAfter);
    }

    @Test
    void deletePerson_whenPersonPassed_RemoveFromJsonFile() {

        personRepositoryUnderTest.personList.add(personToTest);

        boolean result = personRepositoryUnderTest.deletePerson(personToTest);

        assertTrue(result);
    }

}