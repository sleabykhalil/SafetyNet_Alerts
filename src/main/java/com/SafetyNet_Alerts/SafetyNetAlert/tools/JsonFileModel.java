package com.SafetyNet_Alerts.SafetyNetAlert.tools;

import com.SafetyNet_Alerts.SafetyNetAlert.model.Firestation;
import com.SafetyNet_Alerts.SafetyNetAlert.model.MedicalRecord;
import com.SafetyNet_Alerts.SafetyNetAlert.model.Person;
import lombok.Data;

import java.util.List;

@Data
public class JsonFileModel {
    List<Person> personList;
    List<Firestation> firestationList;
    List<MedicalRecord> medicalRecordList;
}
