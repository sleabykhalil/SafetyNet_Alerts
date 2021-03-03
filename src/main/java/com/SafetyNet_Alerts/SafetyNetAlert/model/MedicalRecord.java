package com.SafetyNet_Alerts.SafetyNetAlert.model;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}
