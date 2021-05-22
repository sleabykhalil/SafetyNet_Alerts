package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.exception.ValidationException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
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
                .address("1234 Street St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();
        lenient().doNothing().when(fileRWServiceMock).updateInputFile();

        jsonFileModel = new JsonFileModel();
        personList = new ArrayList<>();
        personList.add(personUsedByTest);
        jsonFileModel.setPersons(personList);
        when(fileRWServiceMock.readInputFromInputJsonFileAndMabToJsonFileModel(anyString())).thenReturn(jsonFileModel);

        personDaoUnderTest = new PersonDaoImpl(fileRWServiceMock);
    }


    @Test
    void create() {
        Person personToBeCreate = Person.builder()
                .firstName("Khalilnew")
                .lastName("Sleabynew")
                .address("1234 Street St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();
        Person result = personDaoUnderTest.create(personToBeCreate);
        assertThat(result).isEqualTo(personToBeCreate);
    }


    @Test
    void update() {
        Person personBefore = personUsedByTest;
        Person personAfter = Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
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
        boolean result = personDaoUnderTest.delete(personUsedByTest);
        assertTrue(result);
    }

    @Test
    void findAll() {
        List<Person> result;
        result = personDaoUnderTest.findAll();
        assertThat(result.toString()).contains("Khalil");
    }

    @Test
    void findAllPerson_whenNoDataFound_validationExceptionThrows() {
        personList.remove(personUsedByTest);

        RuntimeException ex = assertThrows(ValidationException.class, () -> personDaoUnderTest.findAll());
        assertThat(ex.getMessage()).isEqualTo("Data  file is empty");
    }

    @Test
    void getPersonByAddress() {
        List<Person> result;
        result = personDaoUnderTest.getPersonByAddress("1234 Street St");
        assertThat(result.get(0).getFirstName()).isEqualTo("Khalil");
    }

    @Test
    void getPersonByFirstNameAndLastName() {
        Person result;
        result = personDaoUnderTest.getPersonByFirstNameAndLastName("Khalil", "Sleaby");
        assertThat(result.getAddress()).isEqualTo("1234 Street St");
    }

    @Test
    void getPersonByCity() {
        List<Person> result;
        result = personDaoUnderTest.getPersonByCity("Culver");
        assertThat(result.get(0).getFirstName()).isEqualTo("Khalil");
    }

    @Test
    void createPerson_whenPersonExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> personDaoUnderTest.create(personUsedByTest));
        assertThat(ex.getMessage()).isEqualTo("Person with first name: Khalil  last name: Sleaby is already exist.");
    }

    @Test
    void createPerson_whenSameFirsrAndLastNamealreadyExist_validationExceptionThrows() {
        personUsedByTest = Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("4321 Street St")
                .build();
        RuntimeException ex = assertThrows(ValidationException.class, () -> personDaoUnderTest.create(personUsedByTest));
        assertThat(ex.getMessage()).isEqualTo("Person with the same first name: Khalil  last name: Sleaby is already exist.");
    }

    @Test
    void updatePerson_whenPersonExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> personDaoUnderTest.update(personUsedByTest, personUsedByTest));
        assertThat(ex.getMessage()).isEqualTo("Person with, first name: Khalil  last name: Sleaby is already exist.");
    }

    @Test
    void deletePerson_whenPersonNotExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> personDaoUnderTest.delete(Person.builder()
                .firstName("NotExistFirstName")
                .lastName("NotExistLastName").build()));
        assertThat(ex.getMessage()).isEqualTo("Person with, first name: NotExistFirstName  last name: NotExistLastName cant be found.");
    }
}