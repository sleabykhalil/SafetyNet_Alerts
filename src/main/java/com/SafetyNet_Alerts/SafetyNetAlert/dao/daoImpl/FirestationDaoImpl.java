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

    public FirestationDaoImpl(FileRWService fileRWService) {
        this.fileRWService = fileRWService;
        firestationList = fileRWService.jsonAsStringToJsonFileModel(fileRWService.jsonFileToString()).getFirestations();
    }

    public List<Firestation> findPersonByStation(String station) {
        List<Firestation> result = new ArrayList<>();
        firestationList.forEach(firestation -> {
            if (firestation.getStation().equals(station)) {
                result.add(firestation);
            }
        });
        return result;
    }

    @Override
    public List<Firestation> findAll() {
        List<Firestation> result;
        result = firestationList;
        return result;
    }

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

    @Override
    public boolean delete(Firestation firestation) {
        boolean result = firestationList.removeIf(firestationToDelete ->
                firestationToDelete.getAddress().equals(firestation.getAddress()));
        fileRWService.saveToJsonFile();
        return result;
    }
}
