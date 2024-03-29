package com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PeopleWithAddressAndPhone {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
}
