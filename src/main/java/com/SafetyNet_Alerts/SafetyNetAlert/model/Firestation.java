package com.SafetyNet_Alerts.SafetyNetAlert.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Firestation Entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Firestation {

    private String address;
    private String station;
}
