package com.SafetyNet_Alerts.SafetyNetAlert.exception.handlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {
    private int httpStatusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
