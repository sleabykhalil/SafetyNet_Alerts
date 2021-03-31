package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.Data;

import javax.persistence.Entity;

/**
 * Firestation Entity
 */
@Data
@Entity
public class Firestation {

    private String address;
    private String station;
}
