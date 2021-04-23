package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.MedicalRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MedicalRecordService {
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.saveMedicalRecord(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
        MedicalRecord medicalRecordBefore = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return medicalRecordRepository.updateMedicalRecord(medicalRecordBefore, medicalRecord);
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        MedicalRecord medicalRecordToDelete = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return medicalRecordRepository.deleteMedicalRecord(medicalRecordToDelete);
    }


}
