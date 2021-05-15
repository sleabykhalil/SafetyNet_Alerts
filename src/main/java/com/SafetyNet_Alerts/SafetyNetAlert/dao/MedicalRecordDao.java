package com.SafetyNet_Alerts.SafetyNetAlert.dao;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordDao {
    List<MedicalRecord> findAll();

    MedicalRecord create(MedicalRecord medicalRecord);

    MedicalRecord update(MedicalRecord medicalRecordBefore, MedicalRecord medicalRecordAfter);

    boolean delete(MedicalRecord medicalRecord);
}

