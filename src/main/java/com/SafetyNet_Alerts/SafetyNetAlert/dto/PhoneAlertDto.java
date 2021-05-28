package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PhoneAlertDto {
    List<String> phoneNumberList;
}
