package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationRepository {
    public static List<Firestation> firestationList = new ArrayList<>();

    @Autowired
    private Services services;
    @Autowired
    private JsonFileRW jsonFileRW;

    /**
     * All arguments constructor and  Data initialize get all firestation from json file
     *
     * @param services
     * @param jsonFileRW
     */
    public FirestationRepository(final Services services, final JsonFileRW jsonFileRW) {
        this.services = services;
        this.jsonFileRW = jsonFileRW;
        firestationList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString(JsonDataFileName.dataFileName)).getFirestations();
    }

    public List<Firestation> findAll() {
        List<Firestation> result;
        result = firestationList;
        return result;
    }
}
