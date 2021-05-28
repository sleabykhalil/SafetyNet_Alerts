package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.dao.daoImpl.MedicalRecordDaoImpl;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.People;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        log.debug("Create medical record object to update from full name {}=[first name] {}=[last name]", firstName, lastName);
        return medicalRecordDao.update(medicalRecordBefore, medicalRecord);
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        MedicalRecord medicalRecordToDelete = MedicalRecord.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        log.debug("Create medical record object for delete from full name {}=[first name] {}=[last name]", firstName, lastName);
        return medicalRecordDao.delete(medicalRecordToDelete);
    }

    /**
     * Check if there are dublication in ID (first name and last name)
     *
     * @param medicalRecordListToValid
     * @return
     */
    public static boolean isMedicalRecordListValid(List<MedicalRecord> medicalRecordListToValid) {
        log.debug("Validate Medical records");
        List<People> peopleValidationList = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordListToValid) {
            peopleValidationList.add(People.builder()
                    .firstName(medicalRecord.getFirstName())
                    .lastName(medicalRecord.getLastName()).build());
        }
        for (People peopleEx : peopleValidationList) {
            int flag = 0;
            for (People peopleIn : peopleValidationList) {
                if (peopleIn.equals(peopleEx)) {
                    flag += 1;
                    if (flag == 2) {
                        log.error(String
                                .format("person with name %s %s already exist", peopleEx.getFirstName(), peopleEx.getLastName()));
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
