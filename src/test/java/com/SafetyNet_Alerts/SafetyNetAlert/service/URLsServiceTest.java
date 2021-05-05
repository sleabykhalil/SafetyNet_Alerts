package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.ChildAlertDto;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.PeopleWithAgeCatDto;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.DateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
        urLsServiceUnderTest = new URLsService(personDaoMock, firestationDaoMock, medicalRecordDaoMock);
    }

    @Test
    void getListOfPersonCoveredByFireStation() {
        //given
        List<Firestation> firestationListForTest = new ArrayList<>();
        firestationListForTest.add(Firestation.builder()
                .station("1")
                .address("1234 Street St")
                .build());
        List<Person> personListForTest = new ArrayList<>();
        personListForTest.add(Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("1234 Street St")
                .phone("123-456-7890")
                .build());
        MedicalRecord medicalRecordForTest = MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate("01/01/1981").build();

        when(firestationDaoMock.findFirestationByStation(anyString())).thenReturn(firestationListForTest);
        when(personDaoMock.getPersonByAddress(anyString())).thenReturn(personListForTest);
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(medicalRecordForTest);

        //when
        PeopleWithAgeCatDto result;

        result = urLsServiceUnderTest.getListOfPersonCoveredByFireStation("1");

        //then
        assertThat(result.getAdultNumber()).isEqualTo(1);
        assertThat(result.getChildNumber()).isEqualTo(0);
        assertThat(result.getPeopleList().get(0).getFirstName()).isEqualTo("Khalil");
        verify(firestationDaoMock, times(1)).findFirestationByStation(anyString());
        verify(personDaoMock, times(1)).getPersonByAddress(anyString());
        verify(medicalRecordDaoMock, times(1))
                .getMedicalRecordByFirstNameAndLastName(anyString(), anyString());
    }


    @Test
    void getListOFChildByAddress() {
        //given
        List<Person> personListForTest = new ArrayList<>();
        personListForTest.add(Person.builder()
                .firstName("Aram")
                .lastName("Sleaby")
                .address("1234 Street St")
                .phone("123-456-7890")
                .build());
        when(personDaoMock.getPersonByAddress(anyString())).thenReturn(personListForTest);

        MedicalRecord medicalRecordForTest = MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate(LocalDateTime.now().minusYears(3L).
                        format(DateTimeFormatter.ofPattern(DateHelper.DATE_TIME_FORMAT)))
                .build();
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName(anyString(), anyString()))
                .thenReturn(medicalRecordForTest);

        //when
        ChildAlertDto result = urLsServiceUnderTest.getListOFChildByAddress("1234 Street St");

        //then
        assertThat(result.getChildren().get(0).getFirstName()).isEqualTo("Aram");
        assertThat(result.getChildren().get(0).getAge()).isEqualTo(3);
        verify(personDaoMock, times(1)).getPersonByAddress(anyString());
        verify(medicalRecordDaoMock, times(1))
                .getMedicalRecordByFirstNameAndLastName(anyString(), anyString());
    }

    @Test
    void getPhoneAlert() {
        List<Firestation> firestationListForTest = new ArrayList<>();
        firestationListForTest.add(Firestation.builder()
                .station("1")
                .address("1234 Street St")
                .build());
        List<Person> personListForTest = new ArrayList<>();
        personListForTest.add(Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("1234 Street St")
                .phone("123-456-7890")
                .build());

        when(firestationDaoMock.findFirestationByStation(anyString())).thenReturn(firestationListForTest);
        when(personDaoMock.getPersonByAddress(anyString())).thenReturn(personListForTest);

        PhoneAlertDto result = urLsServiceUnderTest.getListListOfPhoneByfireStation("1");

        assertThat(result.get(0)).isEqualTo("123-456-7890");
        verify(firestationDaoMock, times(1)).findFirestationByStation(anyString());
        verify(personDaoMock, times(1)).getPersonByAddress(anyString());
    }

}