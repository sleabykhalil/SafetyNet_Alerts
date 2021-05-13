package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithMedicalBackground;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDto {
    private Map<String, List<PeopleWithMedicalBackground>> addressAndPeopleWithSpecificAgeDtoMap;
}
