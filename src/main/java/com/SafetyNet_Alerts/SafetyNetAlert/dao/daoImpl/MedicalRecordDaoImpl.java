package com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.MedicalRecordDao;
import com.SafetyNet_Alerts.SafetyNetAlert.exception.ValidationException;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.service.FileRWService;
import com.SafetyNet_Alerts.SafetyNetAlert.service.MedicalRecordService;
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
        medicalRecordList = fileRWService.readFromJsonFile().getMedicalrecords();
        MedicalRecordService.isMedicalRecordListValid(medicalRecordList);
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
        if (result.isEmpty()) {
            throw new ValidationException("Data  file is empty");
        }
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
        if (medicalRecordList.contains(medicalRecord)) {
            throw new ValidationException(String.format("Medical record for this person, first name: %s  last name: %s is already exist."
                    , medicalRecord.getFirstName(), medicalRecord.getLastName()));
        }

        List<MedicalRecord> medicalRecordListForValidate = medicalRecordList;
        medicalRecordListForValidate.add(medicalRecord);
        if (!MedicalRecordService.isMedicalRecordListValid(medicalRecordListForValidate)) {
            throw new ValidationException(String.format("Medical record for a person with the same, first name: %s  last name: %s is already exist."
                    , medicalRecord.getFirstName(), medicalRecord.getLastName()));
        }
        medicalRecordList.add(medicalRecord);
        fileRWService.saveToJsonFile();
        return medicalRecord;
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
        if (medicalRecordList.contains(medicalRecordAfter)) {
            throw new ValidationException(String.format("Medical record for person, first name: %s  last name: %s is already exist."
                    , medicalRecordAfter.getFirstName(), medicalRecordAfter.getLastName()));
        }
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
        boolean result = medicalRecordList.removeIf(medicalRecordToDelete -> medicalRecordToDelete.getFirstName()
                .equals(medicalRecord.getFirstName()) &&
                medicalRecordToDelete.getLastName().equals(medicalRecord.getLastName()));
        if (!result) {
            throw new ValidationException(String.format("Medical record for person, first name: %s  last name: %s is not exist."
                    , medicalRecord.getFirstName(), medicalRecord.getLastName()));
        }
        fileRWService.saveToJsonFile();
        return true;
    }

    //URLs

    /**
     * get list of medical record for person by first name and second name
     *
     * @param firstName person first name
     * @param lastName  person last name
     * @return list of medical record
     */
    public MedicalRecord getMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        MedicalRecord medicalRecordByFirstNameAndLastName = new MedicalRecord();
        for (MedicalRecord medicalRecord : medicalRecordList) {
            if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
                medicalRecordByFirstNameAndLastName = medicalRecord;
                break;
            }
        }
        if (medicalRecordByFirstNameAndLastName.getFirstName() == null
                || medicalRecordByFirstNameAndLastName.getLastName() == null) {
            throw new ValidationException(String.format("Medical record for person, first name: %s  last name: %s cant be found."
                    , firstName, lastName));
        }
        return medicalRecordByFirstNameAndLastName;
    }
}
