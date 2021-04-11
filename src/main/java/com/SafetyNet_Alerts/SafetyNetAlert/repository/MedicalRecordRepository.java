package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class MedicalRecordRepository {

    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    /**
     * Data initialize get all Medical records from json file
     */
    @PostConstruct
    private void setup() {
        JsonFileRW jsonFileRW = new JsonFileRW();
        medicalRecordList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString(JsonDataFileName.dataFileName)).getMedicalrecords();
    }


    public List<MedicalRecord> findAll() {
        List<MedicalRecord> result;
        result = medicalRecordList;
        return result;
    }
}
