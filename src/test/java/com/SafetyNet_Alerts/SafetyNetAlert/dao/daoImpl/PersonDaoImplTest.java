package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FileRWService;
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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PersonDaoImplTest {

    @Autowired
    PersonDaoImpl personDaoUnderTest;

    @Mock
    FileRWService fileRWServiceMock;

    JsonFileModel jsonFileModel;
    Person personUsedByTest;
    List<Person> personList;


    @BeforeEach
    void setUp() {
        personUsedByTest = Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("1509 Culver St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();
        lenient().doNothing().when(fileRWServiceMock).saveToJsonFile();

        jsonFileModel = new JsonFileModel();
        personList = new ArrayList<>();
        personList.add(personUsedByTest);
        jsonFileModel.setPersons(personList);
        when(fileRWServiceMock.jsonFileToString()).thenReturn("data");
        when(fileRWServiceMock.jsonAsStringToJsonFileModel("data")).thenReturn(jsonFileModel);

        personDaoUnderTest = new PersonDaoImpl(fileRWServiceMock);
    }

    @Test
    void findAll() {
        List<Person> result;
        result = personDaoUnderTest.findAll();
        assertThat(result.toString()).contains("Khalil");
    }

    @Test
    void create() {
        Person result = personDaoUnderTest.create(personUsedByTest);
        assertThat(result).isEqualTo(personUsedByTest);
    }

    @Test
    void read() {
    }

    @Test
    void update() {
        Person personBefore = personUsedByTest;
        Person personAfter = Person.builder()
                .firstName("Khalil")
                .lastName("Boyd")
                .address("1509 Culver St")//filed to be changed
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();

        Person result = personDaoUnderTest.update(personBefore, personAfter);

        assertThat(result).isEqualTo(personAfter);
    }

    @Test
    void delete() {
        personDaoUnderTest.personList.add(personUsedByTest);

        boolean result = personDaoUnderTest.delete(personUsedByTest);

        assertTrue(result);
    }

    @Test
    void getPersonByAddress() {
        List<Person> result;
        result = personDaoUnderTest.getPersonByAddress("1509 Culver St");
        assertThat(result.get(0).getFirstName()).isEqualTo("Khalil");
    }
}