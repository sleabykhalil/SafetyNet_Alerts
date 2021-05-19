package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.FirestationDao;
import com.SafetyNet_Alerts.SafetyNetAlert.exception.ValidationException;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FileRWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationDaoImpl implements FirestationDao {
    public static List<Firestation> firestationList = new ArrayList<>();

    @Autowired
    private FileRWService fileRWService;

    /**
     * All arguments constructor and  Data initialize get all firestation from json file
     *
     * @param fileRWService File read write manager
     */
    public FirestationDaoImpl(FileRWService fileRWService) {
        this.fileRWService = fileRWService;
        firestationList = fileRWService.readFromJsonFile().getFirestations();
    }

    /**
     * Get list of firestations
     *
     * @return list of firestations
     */
    @Override
    public List<Firestation> findAll() {
        List<Firestation> result;
        result = firestationList;
        if (result.isEmpty()) {
            throw new ValidationException("Data  file is empty");
        }
        return result;
    }

    /**
     * Add firestation to firestation list and create data json file
     *
     * @param firestation to add
     * @return firestation added
     */
    @Override
    public Firestation create(Firestation firestation) {
        if (firestationList.contains(firestation)) {
            throw new ValidationException(String.format("Firestation number %s already associated to address %s ."
                    , firestation.getStation(), firestation.getAddress()));
        }
        firestationList.add(firestation);
        fileRWService.saveToJsonFile();
        return firestation;
    }

    /**
     * Update firestation in firestation list , and create data json file
     *
     * @param firestationBefore data before update
     * @param firestationAfter  data after update
     * @return firestation after update
     */
    @Override
    public Firestation update(Firestation firestationBefore, Firestation firestationAfter) {
        if (firestationList.contains(firestationAfter)) {
            throw new ValidationException(String.format("Firestation number %s all associated to address %s ."
                    , firestationAfter.getStation(), firestationAfter.getAddress()));
        }
        for (Firestation firestation : firestationList) {
            if (firestationBefore.getAddress().equals(firestation.getAddress())) {
                firestationList.set(firestationList.indexOf(firestation), firestationAfter);
                break;
            }
        }
        fileRWService.saveToJsonFile();
        return firestationAfter;
    }

    /**
     * Delete firestation from firestation list and create data json file
     *
     * @param firestation to be deleted
     * @return true if success
     */
    @Override
    public boolean delete(Firestation firestation) {
        boolean result = firestationList.removeIf(firestationToDelete ->
                firestationToDelete.getAddress().equals(firestation.getAddress()));
        if (!result) {
            throw new ValidationException(String.format("Firestation number %s associated to address %s is not exist."
                    , firestation.getStation(), firestation.getAddress()));
        }
        fileRWService.saveToJsonFile();
        return true;
    }

    //URLs

    /**
     * get list of firestation by station number
     *
     * @param stationNumber station number
     * @return list of person corresponding to stationNumber
     */
    public List<Firestation> findFirestationByStation(String stationNumber) {
        List<Firestation> result = new ArrayList<>();
        firestationList.forEach(firestation -> {
            if (firestation.getStation().equals(stationNumber)) {
                result.add(firestation);
            }
        });
        if (result.isEmpty()) {
            throw new ValidationException(String.format("Firestation number %s is not exist ."
                    , stationNumber));
        }
        return result;
    }

    public Firestation findFirestationByAddress(String address) {
        Firestation result = new Firestation();
        for (Firestation firestation : firestationList) {
            if (firestation.getAddress().equals(address)) {
                result = firestation;
                break;
            }
        }
        if (result.getStation() == null) {
            throw new ValidationException(String.format("Firestation associated to address %s not exist."
                    , address));
        }
        return result;
    }
}
