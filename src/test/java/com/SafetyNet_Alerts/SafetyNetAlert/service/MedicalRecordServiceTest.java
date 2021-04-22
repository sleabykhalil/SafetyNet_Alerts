package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class MedicalRecordServiceTest {
    MedicalRecordService medicalRecordServiceUnderTest;
    @Mock
    MedicalRecordRepository medicalRecordRepositoryMock;
    MedicalRecord firstMedicalRecord;
    MedicalRecord secondMedicalRecord;
    List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        firstMedicalRecord = MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate("31/12/1980")
                .medications(List.of("firstMed:30mg", "secondMed:10mg"))
                .allergies(List.of("thirdAllergies", "forthAllergies"))
                .build();
        secondMedicalRecord = MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Khalil")
                .birthdate("01/01/1980")
                .medications(List.of("firstMed:300mg", "secondMed:100mg"))
                .allergies(List.of("thirdAllergies", "forthAllergies"))
                .build();
        medicalRecordList.add(firstMedicalRecord);
        medicalRecordList.add(secondMedicalRecord);
        medicalRecordServiceUnderTest = new MedicalRecordService(medicalRecordRepositoryMock);
    }

    @Test
    void getAllMedicalRecord() {
        when(medicalRecordRepositoryMock.findAll()).thenReturn(medicalRecordList);
        assertThat(medicalRecordServiceUnderTest.getAllMedicalRecords()).hasSize(2);
        verify(medicalRecordRepositoryMock, times(1)).findAll();
    }

    @Test
    void saveMedicalRecord() {
        when(medicalRecordRepositoryMock.saveMedicalRecord(firstMedicalRecord)).thenReturn(firstMedicalRecord);
        assertThat(medicalRecordServiceUnderTest.saveMedicalRecord(firstMedicalRecord)).isEqualTo(firstMedicalRecord);
        verify(medicalRecordRepositoryMock, times(1)).saveMedicalRecord(firstMedicalRecord);
    }

    @Test
    void updateMedicalRecord() {
        when(medicalRecordRepositoryMock.updateMedicalRecord(any(MedicalRecord.class), any(MedicalRecord.class)))
                .thenReturn(secondMedicalRecord);
        assertThat(medicalRecordServiceUnderTest.updateMedicalRecord("first name", "last name", secondMedicalRecord))
                .isEqualTo(secondMedicalRecord);
        verify(medicalRecordRepositoryMock, times(1))
                .updateMedicalRecord(any(MedicalRecord.class), any(MedicalRecord.class));
    }

    @Test
    void deleteFirestation() {
        when(medicalRecordRepositoryMock.deleteMedicalRecord(any(MedicalRecord.class))).thenReturn(true);
        assertThat(medicalRecordServiceUnderTest.deleteMedicalRecord("first name", "last name")).isTrue();
        verify(medicalRecordRepositoryMock, times(1)).deleteMedicalRecord(any(MedicalRecord.class));
    }
}