package com.SafetyNet_Alerts.SafetyNetAlert.dao;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordDao extends JpaRepository<MedicalRecord,Integer> {
}
