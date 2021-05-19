package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
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
class FirestationServiceTest {
    FirestationService firestationServiceUnderTest;
    @Mock
    FirestationDaoImpl firestationDaoMock;

    Firestation firestationFirst;
    Firestation firestationSecond;
    List<Firestation> firestationList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        firestationFirst = Firestation.builder()
                .address("1234 Street St")
                .station("1")
                .build();
        firestationSecond = Firestation.builder()
                .address("1234 Street St")
                .station("2")
                .build();
        firestationList.add(firestationFirst);
        firestationList.add(firestationSecond);
        firestationServiceUnderTest = new FirestationService(firestationDaoMock);
    }

    @Test
    void getAllFirestationTest() {
        when(firestationDaoMock.findAll()).thenReturn(firestationList);
        assertThat(firestationServiceUnderTest.getAllFirestation()).hasSize(2);
        verify(firestationDaoMock, times(1)).findAll();
    }

    @Test
    void saveFirestationTest() {
        when(firestationDaoMock.create(firestationFirst)).thenReturn(firestationFirst);
        assertThat(firestationServiceUnderTest.saveFirestation(firestationFirst)).isEqualTo(firestationFirst);
        verify(firestationDaoMock, times(1)).create(firestationFirst);
    }

    @Test
    void updateFirestationTest() {
        when(firestationDaoMock.update(any(Firestation.class), any(Firestation.class)))
                .thenReturn(firestationSecond);
        assertThat(firestationServiceUnderTest.updateFirestation("new address", firestationFirst))
                .isEqualTo(firestationSecond);
        verify(firestationDaoMock, times(1))
                .update(any(Firestation.class), any(Firestation.class));
    }

    @Test
    void deleteFirestationTest() {
        when(firestationDaoMock.delete(any(Firestation.class))).thenReturn(true);
        assertThat(firestationServiceUnderTest.deleteFirestation("new address")).isTrue();
        verify(firestationDaoMock, times(1)).delete(any(Firestation.class));
    }
}