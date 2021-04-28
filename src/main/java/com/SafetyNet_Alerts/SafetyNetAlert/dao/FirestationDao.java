package com.SafetyNet_Alerts.SafetyNetAlert.dao;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirestationDao {
    List<Firestation> findAll();

    Firestation create(Firestation firestation);

    Firestation read(String station);

    Firestation update(Firestation firestationBefore, Firestation firestationAfter);

    boolean delete(Firestation firestation);
}
