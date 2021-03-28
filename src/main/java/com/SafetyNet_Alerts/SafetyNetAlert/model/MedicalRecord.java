package com.SafetyNet_Alerts.SafetyNetAlert.model;

import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.fuzzy.MaybeEmptyArrayDecoder;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

/**
 * Medical Record Entity
 */
@Data
@Entity
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;

    @ElementCollection
    //@JsonProperty(decoder = MaybeEmptyArrayDecoder.class)
    private List<String> medications;

    @ElementCollection
    //@JsonProperty(decoder = MaybeEmptyArrayDecoder.class)
    private List<String> allergies;
}
