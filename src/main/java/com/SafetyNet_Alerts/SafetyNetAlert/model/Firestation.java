package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Firestation Entity
 */
@Data
@Entity
public class Firestation {

    private String address;
    private String station;
}
