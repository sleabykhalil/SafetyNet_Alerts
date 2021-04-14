package com.SafetyNet_Alerts.SafetyNetAlert.repository;

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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirestationRepositoryTest {
    @Autowired
    FirestationRepository firestationRepositoryUnderTest;

/*    @Mock
    FileRWService fileRWServiceMock;*/

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
        // lenient().doNothing().when(fileRWServiceMock).saveToJsonFile();
        jsonFileModel = new JsonFileModel();
        firestationList = new ArrayList<>();
        firestationList.add(firestationUsedByTest);
        jsonFileModel.setFirestations(firestationList);
        when(fileRWServiceMock.jsonFileToString(anyString())).thenReturn("data");
        when(fileRWServiceMock.jsonAsStringToJsonFileModel("data")).thenReturn(jsonFileModel);

        //firestationRepositoryUnderTest = new FirestationRepository(fileRWServiceMock, jsonFileRWMock);
        firestationRepositoryUnderTest = new FirestationRepository(fileRWServiceMock);
    }

    @Test
    void findAll() {
        List<Firestation> result = firestationRepositoryUnderTest.findAll();
        assertThat(result.toString()).contains("1234 Street St");
    }

    @Test
    void saveFirestation_WhenFirestationPassed_FirestationReturn() {
        Firestation result = firestationRepositoryUnderTest.saveFirestation(firestationUsedByTest);
        assertThat(result).isEqualTo(firestationUsedByTest);
    }

    @Test
    void updateFirestation_WhenTowFirestationsPassed_NewFirestationShouldRutern() {
        Firestation firestationBefore = firestationUsedByTest;
        Firestation firestationAfter = Firestation.builder()
                .address("1234 Street St")
                .station("2")
                .build();

        Firestation result = firestationRepositoryUnderTest.updateFirestation(firestationBefore, firestationAfter);

        assertThat(result).isEqualTo(firestationAfter);
    }

    @Test
    void deleteFirestation_whenFirestationPassed_ReturnTrue() {
        firestationRepositoryUnderTest.firestationList.add(firestationUsedByTest);

        boolean result = firestationRepositoryUnderTest.deleteFirestation(firestationUsedByTest);

        assertTrue(result);
    }
}