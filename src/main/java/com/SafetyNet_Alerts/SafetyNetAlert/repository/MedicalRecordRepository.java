package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.constants.JsonDataFileName;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.servec.Services;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepository {
    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @Autowired
    private Services services;
    @Autowired
    private JsonFileRW jsonFileRW;

    /**
     * Constructor and Data initialize get all Medical records from json file
     *
     * @param services
     * @param jsonFileRW
     */
    public MedicalRecordRepository(final Services services, final JsonFileRW jsonFileRW) {
        this.services = services;
        this.jsonFileRW = jsonFileRW;
        medicalRecordList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString(JsonDataFileName.dataFileName)).getMedicalrecords();
    }

    public List<MedicalRecord> findAll() {
        List<MedicalRecord> result;
        result = medicalRecordList;
        return result;
    }
}
