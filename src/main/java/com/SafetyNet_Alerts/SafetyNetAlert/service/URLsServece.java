package com.SafetyNet_Alerts.SafetyNetAlert.service;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.FirestationRepository;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.MedicalRecordRepository;
import com.SafetyNet_Alerts.SafetyNetAlert.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class URLsServece {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public List<Person> getListOfPersonCoveredByFireStation(String StationNumber) {
        /*
         * get list of firestation by station number
         * for each firestation.address get list of person by first name and last name
         * for each person get medical record by first name and last name
         * from medical record lest get adult number and child number
         * return dto contain list of person and number of adult and  child number
         * */
        return null;
    }
}
