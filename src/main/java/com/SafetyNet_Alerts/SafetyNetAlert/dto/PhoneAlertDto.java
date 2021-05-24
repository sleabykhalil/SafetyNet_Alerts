package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PhoneAlertDto {
    List<String> phoneNumberList;
}
