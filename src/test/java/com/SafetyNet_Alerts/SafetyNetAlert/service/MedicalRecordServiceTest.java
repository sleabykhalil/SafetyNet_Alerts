package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
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
class MedicalRecordServiceTest {
    MedicalRecordService medicalRecordServiceUnderTest;

    @Mock
    MedicalRecordDaoImpl medicalRecordDaoMock;

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
        medicalRecordServiceUnderTest = new MedicalRecordService(medicalRecordDaoMock);
    }

    @Test
    void getAllMedicalRecordTest() {
        when(medicalRecordDaoMock.findAll()).thenReturn(medicalRecordList);
        assertThat(medicalRecordServiceUnderTest.getAllMedicalRecords()).hasSize(2);
        verify(medicalRecordDaoMock, times(1)).findAll();
    }

    @Test
    void saveMedicalRecordTest() {
        when(medicalRecordDaoMock.create(firstMedicalRecord)).thenReturn(firstMedicalRecord);
        assertThat(medicalRecordServiceUnderTest.saveMedicalRecord(firstMedicalRecord)).isEqualTo(firstMedicalRecord);
        verify(medicalRecordDaoMock, times(1)).create(firstMedicalRecord);
    }

    @Test
    void updateMedicalRecordTest() {
        when(medicalRecordDaoMock.update(any(MedicalRecord.class), any(MedicalRecord.class)))
                .thenReturn(secondMedicalRecord);
        assertThat(medicalRecordServiceUnderTest.updateMedicalRecord("first name", "last name", secondMedicalRecord))
                .isEqualTo(secondMedicalRecord);
        verify(medicalRecordDaoMock, times(1))
                .update(any(MedicalRecord.class), any(MedicalRecord.class));
    }

    @Test
    void deleteFirestationTest() {
        when(medicalRecordDaoMock.delete(any(MedicalRecord.class))).thenReturn(true);
        assertThat(medicalRecordServiceUnderTest.deleteMedicalRecord("first name", "last name")).isTrue();
        verify(medicalRecordDaoMock, times(1)).delete(any(MedicalRecord.class));
    }
}