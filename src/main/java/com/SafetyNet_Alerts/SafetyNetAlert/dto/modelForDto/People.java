package com.SafetyNet_Alerts.SafetyNetAlert.dto.modelForDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class People {
    private String firstName;
    private String lastName;
}
