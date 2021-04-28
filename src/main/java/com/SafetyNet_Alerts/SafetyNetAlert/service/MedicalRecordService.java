package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MedicalRecordService {
    @Autowired
    MedicalRecordDaoImpl medicalRecordDao;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordDao.findAll();
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordDao.create(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordBefore = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return medicalRecordDao.update(medicalRecordBefore, medicalRecord);
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        MedicalRecord medicalRecordToDelete = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return medicalRecordDao.delete(medicalRecordToDelete);
    }


}
