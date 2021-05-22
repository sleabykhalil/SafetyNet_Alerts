package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.exception.ValidationException;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirestationDaoImplTest {
    @Autowired
    FirestationDaoImpl firestationDaoUnderTest;

    @Mock
    FileRWService fileRWServiceMock;

    JsonFileModel jsonFileModel;
    Firestation firestationUsedByTest;
    List<Firestation> firestationList;

    @BeforeEach
    void setUp() {
        firestationUsedByTest = Firestation.builder()
                .address("1234 Street St")
                .station("1")
                .build();
        jsonFileModel = new JsonFileModel();
        firestationList = new ArrayList<>();
        firestationList.add(firestationUsedByTest);

        jsonFileModel.setFirestations(firestationList);
        when(fileRWServiceMock.readInputFromInputJsonFileAndMabToJsonFileModel(anyString())).thenReturn(jsonFileModel);

        firestationDaoUnderTest = new FirestationDaoImpl(fileRWServiceMock);
    }


    @Test
    void findAll() {
        List<Firestation> result = firestationDaoUnderTest.findAll();
        assertThat(result.toString()).contains("1234 Street St");
    }

    @Test
    void create() {
        Firestation firestationToBeCreate = Firestation.builder()
                .address("1234 Street St")
                .station("10")
                .build();
        Firestation result = firestationDaoUnderTest.create(firestationToBeCreate);
        assertThat(result).isEqualTo(firestationToBeCreate);
    }


    @Test
    void update() {
        Firestation firestationBefore = firestationUsedByTest;
        Firestation firestationAfter = Firestation.builder()
                .address("1234 Street St")
                .station("2")
                .build();

        Firestation result = firestationDaoUnderTest.update(firestationBefore, firestationAfter);

        assertThat(result).isEqualTo(firestationAfter);
    }

    @Test
    void delete() {
        firestationList.add(firestationUsedByTest);

        boolean result = firestationDaoUnderTest.delete(firestationUsedByTest);

        assertTrue(result);
    }

    @Test
    void findAllFirestation_whenNoDataFound_validationExceptionThrows() {
        firestationList.remove(firestationUsedByTest);

        RuntimeException ex = assertThrows(ValidationException.class, () -> firestationDaoUnderTest.findAll());
        assertThat(ex.getMessage()).isEqualTo("Data  file is empty");
    }

    @Test
    void createFirestation_whenFirestationExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> firestationDaoUnderTest.create(firestationUsedByTest));
        assertThat(ex.getMessage()).isEqualTo("Firestation number 1 already associated to address 1234 Street St .");
    }

    @Test
    void updateFirestation_whenFirestationExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> firestationDaoUnderTest.update(firestationUsedByTest, firestationUsedByTest));
        assertThat(ex.getMessage()).isEqualTo("Firestation number 1 all associated to address 1234 Street St .");
    }

    @Test
    void deleteFirestation_whenFirestationNotExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> firestationDaoUnderTest.delete(Firestation.builder()
                .address("NotExistAdress")
                .station("NotExistStationNumber").build()));
        assertThat(ex.getMessage()).isEqualTo("Firestation number NotExistStationNumber associated to address NotExistAdress is not exist.");
    }

    @Test
    void findFirestationByStation() {
        List<Firestation> result = firestationDaoUnderTest.findFirestationByStation("1");
        assertThat(result.get(0).getAddress()).isEqualTo("1234 Street St");
    }

    @Test
    void findFirestationByStation_whenFireStationNotExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> firestationDaoUnderTest.findFirestationByStation("5"));
        assertThat(ex.getMessage()).isEqualTo("Firestation number 5 is not exist .");

    }

    @Test
    void findFirestationByAddress() {
        Firestation result = firestationDaoUnderTest.findFirestationByAddress("1234 Street St");
        assertThat(result.getStation()).isEqualTo("1");
    }

    @Test
    void findFirestationByAddress_whenFireStationNotExist_validationExceptionThrows() {

        RuntimeException ex = assertThrows(ValidationException.class, () -> firestationDaoUnderTest.findFirestationByAddress("NotExistAddress"));
        assertThat(ex.getMessage()).isEqualTo("Firestation associated to address NotExistAddress not exist.");

    }
}