package com.SafetyNet_Alerts.SafetyNetAlert.web.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.MedicalRecordDao;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordController {
    @Autowired
    MedicalRecordDao medicalRecordDao;

    @GetMapping(value = "/MedicalRecords")
    public List<MedicalRecord> getAllMedialRecords(){
        return medicalRecordDao.findAll();
    }

}
