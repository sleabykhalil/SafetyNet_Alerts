package com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class PeopleWithMedicalBackground {
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
