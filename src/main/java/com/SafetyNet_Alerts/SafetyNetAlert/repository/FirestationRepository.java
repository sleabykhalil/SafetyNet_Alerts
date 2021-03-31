package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@AllArgsConstructor
public class FirestationRepository {
    private static List<Firestation> firestationList;

    @PostConstruct
    private void setup() {
        JsonFileRW jsonFileRW = new JsonFileRW();
        firestationList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString()).getFirestations();
    }
}
