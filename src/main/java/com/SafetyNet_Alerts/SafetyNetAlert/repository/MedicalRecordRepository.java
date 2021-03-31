package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.tools.JsonFileRW;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
@Repository
@AllArgsConstructor
public class MedicalRecordRepository {

        private static List<MedicalRecord> medicalRecordList;

        @PostConstruct
        private void setup() {
            JsonFileRW jsonFileRW = new JsonFileRW();
            medicalRecordList = jsonFileRW.jsonAsStringToJsonFileModel(jsonFileRW.jsonFileToString()).getMedicalrecords();
        }

    }
