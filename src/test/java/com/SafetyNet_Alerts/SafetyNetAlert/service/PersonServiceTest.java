package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    PersonService personServiceUnderTest;
    @Mock
    PersonDaoImpl personDaoMock;

    Person firstPerson;
    Person secondPerson;
    List<Person> personList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        firstPerson = Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("1509 Culver St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();
        secondPerson = Person.builder()
                .firstName("Khalil")
                .lastName("Khalil")
                .address("1509 Culver St")
                .city("Culver")
                .zip("97451")
                .phone("841-874-6512")
                .email("jaboyd@email.com")
                .build();
        personList.add(firstPerson);
        personList.add(secondPerson);
        personServiceUnderTest = new PersonService(personDaoMock);
    }

    @Test
    void getAllPerson() {
        when(personDaoMock.findAll()).thenReturn(personList);
        assertThat(personServiceUnderTest.getAllPerson()).hasSize(2);
        verify(personDaoMock, times(1)).findAll();
    }

    @Test
    void savePerson() {
        when(personDaoMock.create(firstPerson)).thenReturn(firstPerson);
        assertThat(personServiceUnderTest.savePerson(firstPerson)).isEqualTo(firstPerson);
        verify(personDaoMock, times(1)).create(firstPerson);
    }

    @Test
    void updatePerson() {
        when(personDaoMock.update(any(Person.class), any(Person.class)))
                .thenReturn(secondPerson);
        assertThat(personServiceUnderTest.updatePerson("Khalil", "Sleaby", secondPerson))
                .isEqualTo(secondPerson);
        verify(personDaoMock, times(1))
                .update(any(Person.class), any(Person.class));
    }

    @Test
    void deletePerson() {
        when(personDaoMock.delete(any(Person.class))).thenReturn(true);
        assertThat(personServiceUnderTest.deletePerson("Khalil", "Sleaby")).isTrue();
        verify(personDaoMock, times(1)).delete(any(Person.class));
    }
}