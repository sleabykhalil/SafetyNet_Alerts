package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

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
        when(fileRWServiceMock.readFromJsonFile()).thenReturn(jsonFileModel);

        firestationDaoUnderTest = new FirestationDaoImpl(fileRWServiceMock);
    }

    @Test
    void findFirestationByStation() {
        List<Firestation> result = firestationDaoUnderTest.findFirestationByStation("1");
        assertThat(result.get(0).getAddress()).isEqualTo("1234 Street St");
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
        firestationDaoUnderTest.firestationList.add(firestationUsedByTest);

        boolean result = firestationDaoUnderTest.delete(firestationUsedByTest);

        assertTrue(result);
    }
}