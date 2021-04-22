package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.FirestationRepository;
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
    FirestationRepository firestationRepositoryMock;

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
        firestationServiceUnderTest = new FirestationService(firestationRepositoryMock);
    }

    @Test
    void getAllFirestation() {
        when(firestationRepositoryMock.findAll()).thenReturn(firestationList);
        assertThat(firestationServiceUnderTest.getAllFirestation()).hasSize(2);
        verify(firestationRepositoryMock, times(1)).findAll();
    }

    @Test
    void saveFirestation() {
        when(firestationRepositoryMock.saveFirestation(firestationFirst)).thenReturn(firestationFirst);
        assertThat(firestationServiceUnderTest.saveFirestation(firestationFirst)).isEqualTo(firestationFirst);
        verify(firestationRepositoryMock, times(1)).saveFirestation(firestationFirst);
    }

    @Test
    void updateFirestation() {
        when(firestationRepositoryMock.updateFirestation(any(Firestation.class), any(Firestation.class)))
                .thenReturn(firestationSecond);
        assertThat(firestationServiceUnderTest.updateFirestation("new address", firestationFirst))
                .isEqualTo(firestationSecond);
        verify(firestationRepositoryMock, times(1))
                .updateFirestation(any(Firestation.class), any(Firestation.class));
    }

    @Test
    void deleteFirestation() {
        when(firestationRepositoryMock.deleteFirestation(any(Firestation.class))).thenReturn(true);
        assertThat(firestationServiceUnderTest.deleteFirestation("new address")).isTrue();
        verify(firestationRepositoryMock, times(1)).deleteFirestation(any(Firestation.class));
    }
}