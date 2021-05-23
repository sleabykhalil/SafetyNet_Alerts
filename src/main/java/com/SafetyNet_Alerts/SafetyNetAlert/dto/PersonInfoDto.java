package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.MedicalHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PersonInfoDto {
    private String lastName;
    private String address;
    private int age;
    private String email;
    private MedicalHistory medicalHistory;
}