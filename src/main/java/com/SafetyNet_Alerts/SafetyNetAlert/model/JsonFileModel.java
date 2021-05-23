package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonFileModel {
    List<Person> persons;
    List<Firestation> firestations;
    List<MedicalRecord> medicalrecords;

}
