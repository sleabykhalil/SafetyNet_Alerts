package com.SafetyNet_Alerts.SafetyNetAlert.servec;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.JsonFileModel;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.FirestationRepository;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.MedicalRecordRepository;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.PersonRepository;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Services {
    //@Autowired
    private JsonFileRW jsonFileRW = new JsonFileRW();

    public void saveToJsonFile() {
        JsonFileModel jsonFileModel;
        jsonFileModel = JsonFileModel.builder()
                .persons(PersonRepository.personList)
                .firestations(FirestationRepository.firestationList)
                .medicalrecords(MedicalRecordRepository.medicalRecordList)
                .build();
        jsonFileRW.stringToJsonFile(jsonFileRW.jsonFileModelToJsonAsString(jsonFileModel), JsonDataFileName.dataFileName);

    }
}
