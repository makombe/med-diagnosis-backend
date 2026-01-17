package com.communityhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisResponseDto {
    private Long id;
    private String symptoms;
    private String diagnosisName;
    private String icd;
    private String icdName;
    private String validationStatus;
    private LocalDateTime createdAt;
}
