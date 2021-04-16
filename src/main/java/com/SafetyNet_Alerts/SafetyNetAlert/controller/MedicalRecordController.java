package com.SafetyNet_Alerts.SafetyNetAlert.controller;

import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    /**
     * Get  for all Medical records
     *
     * @return list of medical records
     */
    @GetMapping(value = "/medicalRecords")
    public List<MedicalRecord> getAllMedialRecords() {
        return medicalRecordRepository.findAll();
    }

    /**
     * Add new MedicalRecord json string passed in body
     *
     * @param medicalRecord to add
     * @return medicalRecord after added
     */
    @PostMapping(value = "/medicalRecord")
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordRepository.saveMedicalRecord(medicalRecord);
        return medicalRecord;
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
        MedicalRecord updateMedicalRecordBefore = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        medicalRecordRepository.updateMedicalRecord(updateMedicalRecordBefore, medicalRecord);
        return medicalRecord;
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
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        boolean result = medicalRecordRepository.deleteMedicalRecord(medicalRecord);
        return result;
    }

}
