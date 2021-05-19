package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonFileModel {
    List<Person> persons;
    List<Firestation> firestations;
    List<MedicalRecord> medicalrecords;

}
