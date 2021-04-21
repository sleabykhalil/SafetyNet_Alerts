package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordRepositoryTest {
    @Autowired
    MedicalRecordRepository medicalRecordRepositoryUnderTest;

    @Mock
    Services servicesMock;

    @Mock
    JsonFileRW jsonFileRWMock;

    JsonFileModel jsonFileModel;
    MedicalRecord medicalRecordToTest;
    List<MedicalRecord> medicalRecordList;

    @BeforeEach
    void setUp() {
        medicalRecordToTest = MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate("01/01/1980")
                .medications(List.of("firstMed:350mg", "secondMed:100mg"))
                .allergies(List.of("firstAllergies", "secondAllergies"))
                .build();
        lenient().doNothing().when(servicesMock).saveToJsonFile();

        jsonFileModel = new JsonFileModel();
        medicalRecordList = new ArrayList<>();
        medicalRecordList.add(medicalRecordToTest);
        jsonFileModel.setMedicalrecords(medicalRecordList);
        when(jsonFileRWMock.jsonFileToString(anyString())).thenReturn("data");
        when(jsonFileRWMock.jsonAsStringToJsonFileModel("data")).thenReturn(jsonFileModel);

        medicalRecordRepositoryUnderTest = new MedicalRecordRepository(servicesMock, jsonFileRWMock);
    }

    @Test
    void findAllMedicalRecords() {
        List<MedicalRecord> result;
        result = medicalRecordRepositoryUnderTest.findAll();
        assertThat(result.toString()).contains("Khalil");
    }

    @Test
    void saveMedicalRecords_whenMedicalRecordPassed_shouldReturnSameMedicalRecord() {
        MedicalRecord result = medicalRecordRepositoryUnderTest.saveMedicalRecord(medicalRecordToTest);
    }

    @Test
    void updateMedicalRecord_WhenTowMedicalRecordsPassed_ShouldReturnMedicalRecordAfterUpdate() {
        MedicalRecord medicalRecordBefore = medicalRecordToTest;
        MedicalRecord medicalRecordAfter = MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate("31/12/1980")
                .medications(List.of("firstMed:30mg", "secondMed:10mg"))
                .allergies(List.of("thirdAllergies", "forthAllergies"))
                .build();

        MedicalRecord result = medicalRecordRepositoryUnderTest.updateMedicalRecord(medicalRecordBefore, medicalRecordAfter);

        assertThat(result).isEqualTo(medicalRecordAfter);
    }

    @Test
    void deleteMedicalRecord_WhenMedicalRecordPassed_ShouldReturnTrue() {
        medicalRecordRepositoryUnderTest.medicalRecordList.add(medicalRecordToTest);

        boolean result = medicalRecordRepositoryUnderTest.deleteMedicalRecord(medicalRecordToTest);

        assertTrue(result);
    }
}
