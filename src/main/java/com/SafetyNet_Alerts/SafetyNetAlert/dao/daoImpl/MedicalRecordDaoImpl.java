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

    public MedicalRecordDaoImpl(FileRWService fileRWService) {
        this.fileRWService = fileRWService;
        medicalRecordList = fileRWService.jsonAsStringToJsonFileModel(fileRWService.jsonFileToString()).getMedicalrecords();
    }

    @Override
    public List<MedicalRecord> findAll() {
        List<MedicalRecord> result;
        result = medicalRecordList;
        return result;
    }

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

    @Override
    public boolean delete(MedicalRecord medicalRecord) {
        boolean result = medicalRecordList.removeIf(medicalRecordToDelete -> medicalRecordToDelete.getFirstName().equals(medicalRecord.getFirstName()) &&
                medicalRecordToDelete.getLastName().equals(medicalRecord.getLastName()));
        fileRWService.saveToJsonFile();
        return result;
    }
}
