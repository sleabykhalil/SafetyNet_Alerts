package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.PersonDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.*;
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
import java.util.Collections;
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
    void getListOfPersonCoveredByFireStationTest() {
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
        assertThat(result.getPeopleWithAddressAndPhoneList().get(0).getFirstName()).isEqualTo("Khalil");
        verify(firestationDaoMock, times(1)).findFirestationByStation(anyString());
        verify(personDaoMock, times(1)).getPersonByAddress(anyString());
        verify(medicalRecordDaoMock, times(1))
                .getMedicalRecordByFirstNameAndLastName(anyString(), anyString());
    }


    @Test
    void getListOFChildByAddressTest() {
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
                .firstName("Aram")
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
    void getPhoneAlertTest() {
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

        PhoneAlertDto result = urLsServiceUnderTest.getPhoneNumber("1");

        assertThat(result.getPhoneNumberList().get(0)).isEqualTo("123-456-7890");
        verify(firestationDaoMock, times(1)).findFirestationByStation(anyString());
        verify(personDaoMock, times(1)).getPersonByAddress(anyString());
    }

    @Test
    void getPeopleListServedByFirestationNumberByAddressTest() {
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
        personListForTest.add(Person.builder()
                .firstName("Aram")
                .lastName("Sleaby")
                .address("1234 Street St")
                .phone("123-456-7890")
                .build());
        List<MedicalRecord> medicalRecordListForTest = new ArrayList<>();
        medicalRecordListForTest.add(MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate(LocalDateTime.now().minusYears(40L).
                        format(DateTimeFormatter.ofPattern(DateHelper.DATE_TIME_FORMAT)))
                .medications(List.of("firstMed:30mg", "secondMed:10mg"))
                .allergies(List.of("thirdAllergies", "forthAllergies"))
                .build());
        medicalRecordListForTest.add(MedicalRecord.builder()
                .firstName("Aram")
                .lastName("Sleaby")
                .birthdate(LocalDateTime.now().minusYears(3L).
                        format(DateTimeFormatter.ofPattern(DateHelper.DATE_TIME_FORMAT)))
                .medications(Collections.emptyList())
                .allergies(Collections.emptyList())
                .build());


        when(firestationDaoMock.findFirestationByAddress("1234 Street St")).thenReturn(firestationListForTest.get(0));
        when(personDaoMock.getPersonByAddress("1234 Street St")).thenReturn(personListForTest);
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName("Khalil", "Sleaby"))
                .thenReturn(medicalRecordListForTest.get(0));
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName("Aram", "Sleaby"))
                .thenReturn(medicalRecordListForTest.get(1));
        //when
        PeopleWithSpecificAgeDto result = urLsServiceUnderTest.getPeopleListServedByFirestationNumberByAddress("1234 Street St");
        //then
        assertThat(result.getPeopleWithLastNamePhoneAgesList().get(0).getLastName()).isEqualTo("Sleaby");
        assertThat(result.getFirestationNumber()).isEqualTo("1");

        verify(firestationDaoMock, times(1)).findFirestationByAddress("1234 Street St");
        verify(personDaoMock, times(1)).getPersonByAddress("1234 Street St");
    }

    @Test
    void geHouseDto() {
        //given
        List<Firestation> firestationListForTest = new ArrayList<>();
        firestationListForTest.add(Firestation.builder()
                .station("1")
                .address("1234 Street St")
                .build());
        firestationListForTest.add(Firestation.builder()
                .station("2")
                .address("4321 Street St")
                .build());

        List<Person> personListForTest = new ArrayList<>();
        personListForTest.add(Person.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .address("1234 Street St")
                .phone("123-456-7890")
                .build());
        personListForTest.add(Person.builder()
                .firstName("Aram")
                .lastName("Sleaby")
                .address("1234 Street St")
                .phone("123-456-7890")
                .build());
        personListForTest.add(Person.builder()
                .firstName("Khalil")
                .lastName("Other")
                .address("4321 Street St")
                .phone("123-456-7890")
                .build());

        List<MedicalRecord> medicalRecordListForTest = new ArrayList<>();
        medicalRecordListForTest.add(MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate(LocalDateTime.now().minusYears(40L).
                        format(DateTimeFormatter.ofPattern(DateHelper.DATE_TIME_FORMAT)))
                .medications(List.of("firstMed:30mg", "secondMed:10mg"))
                .allergies(List.of("thirdAllergies", "forthAllergies"))
                .build());
        medicalRecordListForTest.add(MedicalRecord.builder()
                .firstName("Aram")
                .lastName("Sleaby")
                .birthdate(LocalDateTime.now().minusYears(3L).
                        format(DateTimeFormatter.ofPattern(DateHelper.DATE_TIME_FORMAT)))
                .medications(Collections.emptyList())
                .allergies(Collections.emptyList())
                .build());
        medicalRecordListForTest.add(MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Other")
                .birthdate(LocalDateTime.now().minusYears(20L).
                        format(DateTimeFormatter.ofPattern(DateHelper.DATE_TIME_FORMAT)))
                .medications(Collections.emptyList())
                .allergies(Collections.emptyList())
                .build());

        when(firestationDaoMock.findFirestationByStation("1")).thenReturn(List.of(firestationListForTest.get(0)));
        when(firestationDaoMock.findFirestationByStation("2")).thenReturn(List.of(firestationListForTest.get(1)));

        when(personDaoMock.getPersonByAddress("1234 Street St"))
                .thenReturn(List.of(personListForTest.get(0), personListForTest.get(1)));
        when(personDaoMock.getPersonByAddress("4321 Street St"))
                .thenReturn(List.of(personListForTest.get(2)));

        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName("Khalil", "Sleaby"))
                .thenReturn(medicalRecordListForTest.get(0));
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName("Aram", "Sleaby"))
                .thenReturn(medicalRecordListForTest.get(1));
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName("Khalil", "Other"))
                .thenReturn(medicalRecordListForTest.get(1));
        //when
        List<String> stationNumberList = List.of("1", "2");
        HouseDto result = urLsServiceUnderTest.getHousesByStationNumber(stationNumberList);
        //then
        assertThat(result.getAddressAndPeopleWithSpecificAgeDtoMap().get("1234 Street St").get(0).getLastName())
                .isEqualTo("Sleaby");
        assertThat(result.getAddressAndPeopleWithSpecificAgeDtoMap().containsKey("1234 Street St")).isTrue();

        verify(firestationDaoMock, times(1)).findFirestationByStation("1");
        verify(personDaoMock, times(1)).getPersonByAddress("1234 Street St");
    }
}