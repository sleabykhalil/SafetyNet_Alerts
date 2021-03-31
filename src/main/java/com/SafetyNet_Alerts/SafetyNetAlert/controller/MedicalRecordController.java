package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordController {
    @Autowired
    MedicalRecordRepository medicalRecordDao;

    /**
     * Get  for all Medical records
     * @return list of medical records
     */
    @GetMapping(value = "/MedicalRecords")
    public List<MedicalRecord> getAllMedialRecords() {
        return medicalRecordDao.findAll();
    }

}
