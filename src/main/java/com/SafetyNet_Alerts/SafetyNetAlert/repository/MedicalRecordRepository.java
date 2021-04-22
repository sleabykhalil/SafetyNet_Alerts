package com.SafetyNet_Alerts.SafetyNetAlert.repository;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FileRWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordRepository {
    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @Autowired
    private FileRWService fileRWService;

    /**
     * Constructor and Data initialize get all Medical records from json file
     *
     * @param fileRWService File read write manager
     */
    public MedicalRecordRepository(FileRWService fileRWService) {
        this.fileRWService = fileRWService;
        medicalRecordList = fileRWService.jsonAsStringToJsonFileModel(fileRWService.jsonFileToString()).getMedicalrecords();
    }

    public List<MedicalRecord> findAll() {
        List<MedicalRecord> result;
        result = medicalRecordList;
        return result;
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordList.add(medicalRecord);
        fileRWService.saveToJsonFile();
        return medicalRecord;
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecordBefore, MedicalRecord medicalRecordAfter) {
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if ((medicalRecordBefore.getFirstName().equals(medicalRecord.getFirstName())) &&
                    (medicalRecordBefore.getLastName().equals(medicalRecord.getLastName()))) {
                medicalRecordList.set(medicalRecordList.indexOf(medicalRecord), medicalRecordAfter);
                break;
            }
        }
        fileRWService.saveToJsonFile();
        return medicalRecordAfter;
    }

    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        boolean result = medicalRecordList.removeIf(medicalRecordToDelete -> medicalRecordToDelete.getFirstName().equals(medicalRecord.getFirstName()) &&
                medicalRecordToDelete.getLastName().equals(medicalRecord.getLastName()));
        fileRWService.saveToJsonFile();
        return result;
    }
}
