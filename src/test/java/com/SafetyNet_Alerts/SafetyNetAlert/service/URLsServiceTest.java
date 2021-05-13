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

    List<Firestation> firestationListForTest = new ArrayList<>();
    List<Person> personListForTest = new ArrayList<>();
    List<MedicalRecord> medicalRecordListForTest = new ArrayList<>();

    @BeforeEach
    void setUp() {
        urLsServiceUnderTest = new URLsService(personDaoMock, firestationDaoMock, medicalRecordDaoMock);

        firestationListForTest.add(Firestation.builder()
                .station("1")
                .address("1234 Street St")
                .build());
        firestationListForTest.add(Firestation.builder()
                .station("2")
                .address("4321 Street St")
                .build());

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
    }

    @Test
    void getListOfPersonCoveredByFireStationTest() {
        //given

        when(firestationDaoMock.findFirestationByStation("1"))
                .thenReturn(List.of(firestationListForTest.get(0)));
        when(personDaoMock.getPersonByAddress("1234 Street St"))
                .thenReturn(List.of(personListForTest.get(0)));
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName("Khalil", "Sleaby"))
                .thenReturn(medicalRecordListForTest.get(0));

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
        when(personDaoMock.getPersonByAddress("1234 Street St"))
                .thenReturn(List.of(personListForTest.get(1)));
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName("Aram", "Sleaby"))
                .thenReturn(medicalRecordListForTest.get(1));

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
        //given
        when(firestationDaoMock.findFirestationByStation("1"))
                .thenReturn(List.of(firestationListForTest.get(0)));
        when(personDaoMock.getPersonByAddress("1234 Street St"))
                .thenReturn(List.of(personListForTest.get(0), personListForTest.get(1)));
        //when
        PhoneAlertDto result = urLsServiceUnderTest.getPhoneNumber("1");
        //then
        assertThat(result.getPhoneNumberList().get(0)).isEqualTo("123-456-7890");
        verify(firestationDaoMock, times(1)).findFirestationByStation(anyString());
        verify(personDaoMock, times(1)).getPersonByAddress(anyString());
    }

    @Test
    void getPeopleListServedByFirestationNumberByAddressTest() {
        //given
        when(firestationDaoMock.findFirestationByAddress("1234 Street St")).thenReturn(firestationListForTest.get(0));
        when(personDaoMock.getPersonByAddress("1234 Street St"))
                .thenReturn(List.of(personListForTest.get(0), personListForTest.get(1)));
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

    @Test
    void getListOfPersonInfoTest() {
        //given
        when(personDaoMock.getListOfPersonByFirstNameAndLastName("Khalil", "Sleaby"))
                .thenReturn(List.of(personListForTest.get(0)));
        when(medicalRecordDaoMock.getMedicalRecordByFirstNameAndLastName("Khalil", "Sleaby"))
                .thenReturn(medicalRecordListForTest.get(0));
        //when
        List<PersonInfoDto> result = urLsServiceUnderTest.getListOfPersonInfo("Khalil", "Sleaby");
        //then
        assertThat(result.get(0).getLastName()).isEqualTo("Sleaby");
        assertThat(result.get(0).getAge()).isEqualTo(40);
    }
}