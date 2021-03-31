package com.SafetyNet_Alerts.SafetyNetAlert.model;


import lombok.Data;

import javax.persistence.Entity;

/**
 * Person Entity
 */
@Data
@Entity
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
