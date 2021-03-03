package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * Medical Record Entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue
    private int Id;

    private String firstName;
    private String lastName;
    private String birthDate;

    @ElementCollection
    private List<String> medications;
    @ElementCollection
    private List<String> allergies;
}
