package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Medical Record Entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;

    //@JsonProperty(decoder = MaybeEmptyArrayDecoder.class)
    private List<String> medications;

    //@JsonProperty(decoder = MaybeEmptyArrayDecoder.class)
    private List<String> allergies;
}
