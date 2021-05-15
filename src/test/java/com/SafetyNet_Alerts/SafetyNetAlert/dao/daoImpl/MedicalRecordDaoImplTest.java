package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.exception.ValidationException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        medicalRecordList.add(medicalRecordToTest);

        boolean result = medicalRecordDaoUnderTest.delete(medicalRecordToTest);

        assertTrue(result);
    }

    @Test
    void getMedicalRecordByFirstNameAndLastName() {
        MedicalRecord result;
        result = medicalRecordDaoUnderTest.getMedicalRecordByFirstNameAndLastName("Khalil", "Sleaby");
        assertThat(result.getBirthdate()).isEqualTo("01/01/1980");
    }

    @Test
    void findAll() {
        List<MedicalRecord> result;
        result = medicalRecordDaoUnderTest.findAll();
        assertThat(result.toString()).contains("Khalil");
    }

    @Test
    void findAllMedicalRecord_whenNoDataFound_validationExceptionThrows() {
        medicalRecordList.remove(medicalRecordToTest);

        RuntimeException ex = assertThrows(ValidationException.class, () -> medicalRecordDaoUnderTest.findAll());
        assertThat(ex.getMessage()).isEqualTo("Data  file is empty");
    }

    @Test
    void createMedicalRecord_whenMedicalRecordExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> medicalRecordDaoUnderTest.create(medicalRecordToTest));
        assertThat(ex.getMessage()).isEqualTo("Medical record for this person, first name: Khalil  last name: Sleaby is already exist.");
    }

    @Test
    void createMedicalRecord_whenSameFirsrAndLastNamealreadyExist_validationExceptionThrows() {
        medicalRecordToTest = MedicalRecord.builder()
                .firstName("Khalil")
                .lastName("Sleaby")
                .birthdate("01/01/2000")
                .build();
        RuntimeException ex = assertThrows(ValidationException.class, () -> medicalRecordDaoUnderTest.create(medicalRecordToTest));
        assertThat(ex.getMessage()).isEqualTo("Medical record for a person with the same, first name: Khalil  last name: Sleaby is already exist.");
    }

    @Test
    void updateMedicalRecord_whenMedicalRecordExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> medicalRecordDaoUnderTest.update(medicalRecordToTest, medicalRecordToTest));
        assertThat(ex.getMessage()).isEqualTo("Medical record for person, first name: Khalil  last name: Sleaby is already exist.");
    }

    @Test
    void deleteMedicalRecord_whenMedicalRecordsNotExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> medicalRecordDaoUnderTest.delete(MedicalRecord.builder()
                .firstName("NotExistFirstName")
                .lastName("NotExistLastName").build()));
        assertThat(ex.getMessage()).isEqualTo("Medical record for person, first name: NotExistFirstName  last name: NotExistLastName is not exist.");
    }

    @Test
    void getMedicalRecordByFirstNameAndLastName_whenMedicalRecordExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> medicalRecordDaoUnderTest.getMedicalRecordByFirstNameAndLastName("NotExistFirstName", "NotExistLastName"));
        assertThat(ex.getMessage()).isEqualTo("Medical record for person, first name: NotExistFirstName  last name: NotExistLastName cant be found.");
    }
}