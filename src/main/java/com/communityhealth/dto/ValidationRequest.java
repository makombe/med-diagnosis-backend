package com.communityhealth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class ValidationRequest {

    @NotNull(message = "Diagnosis ID is required")
    private Long diagnosisId;

    @NotBlank(message = "Status can be (VALID or INVALID)")
    private String status;
}


