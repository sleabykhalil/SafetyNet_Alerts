package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class FirestationRepository {
    public static List<Firestation> firestationList = new ArrayList<>();

    /**
     * Data initialize get all firestation from json file
     */
    @PostConstruct
    private void setup() {
        JsonFileRW jsonFileRW = new JsonFileRW();
        firestationList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString(JsonDataFileName.dataFileName)).getFirestations();
    }

    public List<Firestation> findAll() {
        List<Firestation> result;
        result = firestationList;
        return result;
    }
}
