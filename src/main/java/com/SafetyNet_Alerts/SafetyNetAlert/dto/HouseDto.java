package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithMedicalBackground;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class HouseDto {
    private Map<String, List<PeopleWithMedicalBackground>> addressAndPeopleWithSpecificAgeDtoMap;
}
