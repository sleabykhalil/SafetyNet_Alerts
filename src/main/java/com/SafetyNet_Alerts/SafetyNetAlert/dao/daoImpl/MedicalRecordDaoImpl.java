package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.MedicalRecordDao;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FileRWService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordDaoImpl implements MedicalRecordDao {
    public static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @Autowired
    private FileRWService fileRWService;

    /**
     * Constructor and Data initialize get all Medical records from json file
     *
     * @param fileRWService File read write manager
     */
    public MedicalRecordDaoImpl(FileRWService fileRWService) {
        this.fileRWService = fileRWService;
        medicalRecordList = fileRWService.jsonAsStringToJsonFileModel(fileRWService.jsonFileToString()).getMedicalrecords();
    }

    /**
     * Get list of medical records
     *
     * @return list of medicalRecords
     */
    @Override
    public List<MedicalRecord> findAll() {
        List<MedicalRecord> result;
        result = medicalRecordList;
        return result;
    }

    /**
     * Add medicalrecord to medicalrecord list and create data json file
     *
     * @param medicalRecord medicalrecord to add
     * @return medicalrecord added
     */
    @Override
    public MedicalRecord create(MedicalRecord medicalRecord) {
        medicalRecordList.add(medicalRecord);
        fileRWService.saveToJsonFile();
        return medicalRecord;
    }

    @Override
    public MedicalRecord read(String firstName, String lastName) {
        return null;
    }

    /**
     * Update medical records in medicalRecord list , and create data json file
     *
     * @param medicalRecordBefore medical record data before update
     * @param medicalRecordAfter  medical record data after add
     * @return medicalRecord after add
     */
    @Override
    public MedicalRecord update(MedicalRecord medicalRecordBefore, MedicalRecord medicalRecordAfter) {
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

    /**
     * Delete medical record from medicalRecord list and create data json file
     *
     * @param medicalRecord to be deleted
     * @return true if success
     */
    @Override
    public boolean delete(MedicalRecord medicalRecord) {
        boolean result = medicalRecordList.removeIf(medicalRecordToDelete -> medicalRecordToDelete.getFirstName().equals(medicalRecord.getFirstName()) &&
                medicalRecordToDelete.getLastName().equals(medicalRecord.getLastName()));
        fileRWService.saveToJsonFile();
        return result;
    }
}
