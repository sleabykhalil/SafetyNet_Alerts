package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirestationRepositoryTest {
    @Autowired
    FirestationRepository firestationRepositoryUnderTest;

    @Mock
    Services servicesMock;

    @Mock
    JsonFileRW jsonFileRWMock;

    JsonFileModel jsonFileModel;
    Firestation firestationUsedByTest;
    List<Firestation> firestationList;

    @BeforeEach
    void setUp() {
        firestationUsedByTest = Firestation.builder()
                .address("1234 Street St")
                .station("1")
                .build();
        lenient().doNothing().when(servicesMock).saveToJsonFile();
        jsonFileModel = new JsonFileModel();
        firestationList = new ArrayList<>();
        firestationList.add(firestationUsedByTest);
        jsonFileModel.setFirestations(firestationList);
        when(jsonFileRWMock.jsonFileToString(anyString())).thenReturn("data");
        when(jsonFileRWMock.jsonAsStringToJsonFileModel("data")).thenReturn(jsonFileModel);

        firestationRepositoryUnderTest = new FirestationRepository(servicesMock, jsonFileRWMock);
    }

    @Test
    void findAll() {
        List<Firestation> result = firestationRepositoryUnderTest.findAll();
        assertThat(result.toString()).contains("1234 Street St");
    }

}