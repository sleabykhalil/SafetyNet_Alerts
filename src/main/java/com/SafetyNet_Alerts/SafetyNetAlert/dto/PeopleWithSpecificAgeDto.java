package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithMedicalBackground;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PeopleWithSpecificAgeDto {
    private String firestationNumber;
    private List<PeopleWithMedicalBackground> peopleWithLastNamePhoneAgesList;
}
