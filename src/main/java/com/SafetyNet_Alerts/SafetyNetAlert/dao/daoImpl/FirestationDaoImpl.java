package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.FirestationDao;
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
        firestationList = fileRWService.jsonAsStringToJsonFileModel(fileRWService.jsonFileToString()).getFirestations();
    }

    /**
     * get list of firestation by station number
     *
     * @param station station number
     * @return list of person corresponding to station
     */
    public List<Firestation> findFirestationByStation(String station) {
        List<Firestation> result = new ArrayList<>();
        firestationList.forEach(firestation -> {
            if (firestation.getStation().equals(station)) {
                result.add(firestation);
            }
        });
        return result;
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
        firestationList.add(firestation);
        fileRWService.saveToJsonFile();
        return firestation;
    }

    @Override
    public Firestation read(String station) {
        return null;
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
        fileRWService.saveToJsonFile();
        return result;
    }
}
