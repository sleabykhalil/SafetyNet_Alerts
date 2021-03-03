package com.SafetyNet_Alerts.SafetyNetAlert.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Medical Record Entity
 */
@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue
    private int Id;

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}
