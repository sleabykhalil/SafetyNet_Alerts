package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {
    @Autowired
    PersonRepository personRepositoryUnderTest;
    /*
        @Mock
        JsonFileRW jsonFileRWMock;*/
    @Mock
    Services services;

    @BeforeEach
    void setUp() {
        personRepositoryUnderTest = new PersonRepository();

    }

    @Test
    void findAll() {
    }

    @Test
    void savePerson_whenPersonPassed_AddToJsonFile() {
        Person person = Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("1509 Culver St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();

        // when(jsonFileRWMock.jsonFileModelToJsonAsString(any(JsonFileModel.class))).thenReturn("");
        //doNothing().when(services).saveToJsonFile();

        Person result = personRepositoryUnderTest.savePerson(person);

        assertThat(result).isEqualTo(person);
        // verify(jsonFileRWMock, times(1)).stringToJsonFile(anyString(), JsonDataFileName.dataFileName);
    }

    @Test
    void updatePerson_whenTowPersonsPassed_AddPersonAfterAndRemovePersonBeforeToJsonFile() {
        Person personBefore = Person.builder()
                .firstName("Khalil")
                .lastName("Boyd")
                .address("1509 Culver St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();
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
        Person person = Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("1509 Culver St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();
        personRepositoryUnderTest.personList.add(person);

        boolean result = personRepositoryUnderTest.deletePerson(person);

        assertTrue(result);
        // verify(jsonFileRWMock, times(1)).stringToJsonFile(anyString(), JsonDataFileName.dataFileName);
    }

}