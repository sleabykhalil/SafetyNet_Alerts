package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PersonWithAgeCatDto;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class URLsServiceTest {

    @Mock
    FirestationDaoImpl firestationDaoMock;
    @Mock
    PersonDaoImpl personDaoMock;
    @Mock
    MedicalRecordDaoImpl medicalRecordDaoMock;

    URLsService urLsServiceUnderTest;

    @BeforeEach
    void setUp() {
        List<Person> listOfPerson = new ArrayList<>();
        Person firstPerson;
        Person secondPerson;
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
        listOfPerson.add(firstPerson);
        listOfPerson.add(secondPerson);

        Firestation firestation;
        firestation = Firestation.builder()
                .address("1234 Street St")
                .station("1")
                .build();

        urLsServiceUnderTest = new URLsService(personDaoMock, firestationDaoMock, medicalRecordDaoMock);
    }

    @Test
    void getListOfPersonCoveredByFireStation() {
        PersonWithAgeCatDto personWithAgeCatDto;
        personWithAgeCatDto = urLsServiceUnderTest.getListOfPersonCoveredByFireStation("1");
        assertThat(personWithAgeCatDto.getAdultNumber()).isEqualTo(1);
        assertThat(personWithAgeCatDto.getChildNumber()).isEqualTo(1);

    }


}