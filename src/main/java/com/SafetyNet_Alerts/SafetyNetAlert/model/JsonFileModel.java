package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.Data;

import java.util.List;

@Data
public class JsonFileModel {
    List<Person> persons;
    List<Firestation> firestations;
    List<MedicalRecord> medicalrecords;
}
