package com.SafetyNet_Alerts.SafetyNetAlert.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Person Entity
 */
@Entity
public class Person {
    @Id
    @GeneratedValue
    private int Id;

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;
}
