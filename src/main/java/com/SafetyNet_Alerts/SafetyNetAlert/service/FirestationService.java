package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FirestationService {

    @Autowired
    FirestationDaoImpl firestationDao;

    public List<Firestation> getAllFirestation() {
        return firestationDao.findAll();
    }

    public Firestation saveFirestation(Firestation firestation) {
        Firestation result;
        result = firestationDao.create(firestation);
        return result;
    }

    public Firestation updateFirestation(String address, Firestation firestationAfter) {
        Firestation result;
        Firestation firestationBefore = Firestation.builder()
                .address(address)
                .build();
        result = firestationDao.update(firestationBefore, firestationAfter);
        return result;
    }

    public boolean deleteFirestation(String address) {
        Firestation firestation = Firestation.builder()
                .address(address)
                .build();
        boolean result = firestationDao.delete(firestation);
        return result;
    }


}
