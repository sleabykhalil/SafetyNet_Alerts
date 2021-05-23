package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithAddressAndPhone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor

public class PeopleWithAgeCatDto {
    private List<PeopleWithAddressAndPhone> peopleWithAddressAndPhoneList;
    private int adultNumber;
    private int childNumber;
}
