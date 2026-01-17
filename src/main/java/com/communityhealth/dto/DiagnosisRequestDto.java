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
public class DiagnosisRequestDto {
    @NotEmpty(message = "Symptoms are required")
    private String symptoms;

    @NotNull
    private PatientDto patient;

}
