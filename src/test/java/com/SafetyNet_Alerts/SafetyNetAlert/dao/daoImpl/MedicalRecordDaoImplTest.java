package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordDaoImplTest {

    @Autowired
    MedicalRecordDaoImpl medicalRecordDaoUnderTest;

    @Mock
    FileRWService fileRWServiceMock;

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
        lenient().doNothing().when(fileRWServiceMock).saveToJsonFile();

        jsonFileModel = new JsonFileModel();
        medicalRecordList = new ArrayList<>();
        medicalRecordList.add(medicalRecordToTest);
        jsonFileModel.setMedicalrecords(medicalRecordList);
        when(fileRWServiceMock.readFromJsonFile()).thenReturn(jsonFileModel);

        medicalRecordDaoUnderTest = new MedicalRecordDaoImpl(fileRWServiceMock);
    }


    @Test
    void findAll() {
        List<MedicalRecord> result;
        result = medicalRecordDaoUnderTest.findAll();
        assertThat(result.toString()).contains("Khalil");
    }

    @Test
    void create() {
        MedicalRecord medicalRecordToBeCreate = MedicalRecord.builder()
                .firstName("Khalilnew")
                .lastName("Sleabynew")
                .birthdate("01/01/1981")
                .medications(List.of("firstMed:350mg", "secondMed:100mg"))
                .allergies(List.of("firstAllergies", "secondAllergies"))
                .build();
        MedicalRecord result = medicalRecordDaoUnderTest.create(medicalRecordToBeCreate);
        assertThat(result).isEqualTo(medicalRecordToBeCreate);
    }


    @Test
    void update() {
        MedicalRecord medicalRecordBefore = medicalRecordToTest;
        MedicalRecord medicalRecordAfter = MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate("31/12/1980")
                .medications(List.of("firstMed:30mg", "secondMed:10mg"))
                .allergies(List.of("thirdAllergies", "forthAllergies"))
                .build();

        MedicalRecord result = medicalRecordDaoUnderTest.update(medicalRecordBefore, medicalRecordAfter);

        assertThat(result).isEqualTo(medicalRecordAfter);
    }

    @Test
    void delete() {
        medicalRecordDaoUnderTest.medicalRecordList.add(medicalRecordToTest);

        boolean result = medicalRecordDaoUnderTest.delete(medicalRecordToTest);

        assertTrue(result);
    }

    @Test
    void getPersonByAddress() {
        MedicalRecord result;
        result = medicalRecordDaoUnderTest.getMedicalRecordByFirstNameAndLastName("Khalil", "Sleaby");
        assertThat(result.getBirthdate()).isEqualTo("01/01/1980");
    }
}