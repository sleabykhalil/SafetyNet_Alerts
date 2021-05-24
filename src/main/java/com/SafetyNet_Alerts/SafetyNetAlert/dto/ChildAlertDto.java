package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.Child;
import com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto.PeopleWithAddressAndPhone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ChildAlertDto {
    private List<Child> children;
    //people live in the same home with children
    private List<PeopleWithAddressAndPhone> peopleWithAddressAndPhoneList;

}
