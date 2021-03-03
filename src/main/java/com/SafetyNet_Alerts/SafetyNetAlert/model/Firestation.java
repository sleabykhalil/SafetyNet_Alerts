package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Firestation Entity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Firestation {
    @Id
    @GeneratedValue
    private int Id;

    private String address;
    private String station;
}