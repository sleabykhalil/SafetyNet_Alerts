package com.SafetyNet_Alerts.SafetyNetAlert.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneAlertDto {
    List<String> phoneNumberList;
}
