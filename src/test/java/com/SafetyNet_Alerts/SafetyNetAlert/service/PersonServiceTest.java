package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.PersonRepository;
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
    PersonRepository personRepositoryMock;

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
        personServiceUnderTest = new PersonService(personRepositoryMock);
    }

    @Test
    void getAllPerson() {
        when(personRepositoryMock.findAll()).thenReturn(personList);
        assertThat(personServiceUnderTest.getAllPerson()).hasSize(2);
        verify(personRepositoryMock, times(1)).findAll();
    }

    @Test
    void savePerson() {
        when(personRepositoryMock.savePerson(firstPerson)).thenReturn(firstPerson);
        assertThat(personServiceUnderTest.savePerson(firstPerson)).isEqualTo(firstPerson);
        verify(personRepositoryMock, times(1)).savePerson(firstPerson);
    }

    @Test
    void updatePerson() {
        when(personRepositoryMock.updatePerson(any(Person.class), any(Person.class)))
                .thenReturn(secondPerson);
        assertThat(personServiceUnderTest.updatePerson("Khalil", "Sleaby", secondPerson))
                .isEqualTo(secondPerson);
        verify(personRepositoryMock, times(1))
                .updatePerson(any(Person.class), any(Person.class));
    }

    @Test
    void deletePerson() {
        when(personRepositoryMock.deletePerson(any(Person.class))).thenReturn(true);
        assertThat(personServiceUnderTest.deletePerson("Khalil", "Sleaby")).isTrue();
        verify(personRepositoryMock, times(1)).deletePerson(any(Person.class));
    }
}