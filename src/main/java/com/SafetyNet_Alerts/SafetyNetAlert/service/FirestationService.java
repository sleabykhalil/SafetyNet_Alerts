package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.FirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {

    @Autowired
    FirestationRepository firestationRepository;

    public List<Firestation> getAllFirestation() {
        return firestationRepository.findAll();
    }

    public Firestation saveFirestation(Firestation firestation) {
        Firestation result;
        result = firestationRepository.saveFirestation(firestation);
        return result;
    }

    public Firestation updateFirestation(String address, Firestation firestationAfter) {
        Firestation result;
        Firestation firestationBefore = Firestation.builder()
                .address(address)
                .build();
        result = firestationRepository.updateFirestation(firestationBefore, firestationAfter);
        return result;
    }

    public boolean deleteFirestation(String address) {
        Firestation firestation = Firestation.builder()
                .address(address)
                .build();
        boolean result = firestationRepository.deleteFirestation(firestation);
        return result;
    }


}
