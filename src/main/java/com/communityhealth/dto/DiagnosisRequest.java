package com.communityhealth.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisRequest {
    @NotEmpty(message = "Symptoms are required")
    private String symptoms;

    @NotNull(message = "Gender is required")
    private String gender; // "male" or "female"

    @NotNull(message = "date of birth")
    private LocalDate dateOfBirth;

}
