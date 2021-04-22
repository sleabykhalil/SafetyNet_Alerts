package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    /**
     * Get  for all Medical records
     *
     * @return list of medical records
     */
    @GetMapping(value = "/medicalRecords")
    public List<MedicalRecord> getAllMedialRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }

    /**
     * Add new MedicalRecord json string passed in body
     *
     * @param medicalRecord to add
     * @return medicalRecord after added
     */
    @PostMapping(value = "/medicalRecord")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    /**
     * Update medicalRecord new medicalRecord passed in body
     *
     * @param firstName     passed in url
     * @param lastName      passed in url
     * @param medicalRecord new information passed in body
     * @return medicalRecord after update
     */
    @PutMapping(value = "/medicalRecord")
    public MedicalRecord updateMedicalRecord(@RequestParam String firstName,
                                             @RequestParam String lastName,
                                             @RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecord);
    }

    /**
     * Delete medicalRecord , name must pass in url
     *
     * @param firstName passed in url
     * @param lastName  passed in url
     * @return true if medicalRecord deleted
     */
    @DeleteMapping(value = "/medicalRecord")
    public boolean deletePerson(@RequestParam String firstName,
                                @RequestParam String lastName) {
        return medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

}
