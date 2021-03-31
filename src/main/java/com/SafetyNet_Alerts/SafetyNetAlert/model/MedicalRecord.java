package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.Data;

import java.util.List;

/**
 * Medical Record Entity
 */
@Data
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;

    //@JsonProperty(decoder = MaybeEmptyArrayDecoder.class)
    private List<String> medications;

    //@JsonProperty(decoder = MaybeEmptyArrayDecoder.class)
    private List<String> allergies;
}
