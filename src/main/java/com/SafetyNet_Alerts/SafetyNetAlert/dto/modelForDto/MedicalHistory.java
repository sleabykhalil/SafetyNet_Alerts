package com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MedicalHistory {
    private List<String> medications;
    private List<String> allergies;
}
