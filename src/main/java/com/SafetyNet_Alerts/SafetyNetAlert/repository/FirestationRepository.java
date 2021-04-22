package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FileRWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationRepository {
    public static List<Firestation> firestationList = new ArrayList<>();

    @Autowired
    private FileRWService fileRWService;

    /**
     * All arguments constructor and  Data initialize get all firestation from json file
     *
     * @param fileRWService File read write manager
     */
    public FirestationRepository(FileRWService fileRWService) {
        this.fileRWService = fileRWService;
        firestationList = fileRWService.jsonAsStringToJsonFileModel(fileRWService.jsonFileToString()).getFirestations();
    }

    /**
     * Get list of firestations
     *
     * @return list of firestations
     */
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
    public Firestation saveFirestation(Firestation firestation) {
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
    public Firestation updateFirestation(Firestation firestationBefore, Firestation firestationAfter) {
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
    public boolean deleteFirestation(Firestation firestation) {
        boolean result = firestationList.removeIf(firestationToDelete ->
                firestationToDelete.getAddress().equals(firestation.getAddress()));
        fileRWService.saveToJsonFile();
        return result;
    }
}
