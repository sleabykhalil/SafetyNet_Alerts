package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.FirestationDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class FirestationService {

    @Autowired
    FirestationDaoImpl firestationDao;

    public List<Firestation> getAllFirestation() {
        log.debug("Get all firestation");
        return firestationDao.findAll();
    }

    public Firestation saveFirestation(Firestation firestation) {
        log.debug("Save firestation {}", firestation);
        Firestation result;
        result = firestationDao.create(firestation);
        return result;
    }

    public Firestation updateFirestation(String address, Firestation firestationAfter) {
        log.debug("Update firestation for {}=[Address] with new data : {}", address, firestationAfter.toString());
        Firestation result;
        Firestation firestationBefore = Firestation.builder()
                .address(address)
                .build();
        result = firestationDao.update(firestationBefore, firestationAfter);
        return result;
    }

    public boolean deleteFirestation(String address) {
        log.debug("Delete firestation for address: {}=[Address] ", address);
        Firestation firestation = Firestation.builder()
                .address(address)
                .build();
        boolean result = firestationDao.delete(firestation);
        return result;
    }


}
