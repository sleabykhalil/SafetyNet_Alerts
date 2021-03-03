package com.SafetyNet_Alerts.SafetyNetAlert.model;


import javax.persistence.Entity;

@Entity
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;
}
