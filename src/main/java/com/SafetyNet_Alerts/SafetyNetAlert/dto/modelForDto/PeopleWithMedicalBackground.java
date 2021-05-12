package com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeopleWithMedicalBackground {
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
    String firestationNumber;
}
