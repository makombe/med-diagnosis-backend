
package com.communityhealth.controller;

import com.communityhealth.dto.DiagnosisRequestDto;
import com.communityhealth.dto.DiagnosisResponseDto;
import com.communityhealth.dto.ValidationRequest;
import com.communityhealth.model.Diagnosis;
import com.communityhealth.service.DiagnosisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    public DiagnosisController(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }

    /**
     * Perform diagnosis based on symptoms
     */
    @PostMapping
    public ResponseEntity<List<DiagnosisResponseDto>> performDiagnosis(
            @Valid @RequestBody DiagnosisRequestDto request) {
        List<DiagnosisResponseDto> results = diagnosisService.performDiagnosis(request);
        return ResponseEntity.ok(results);
    }

    /**
     * Validate a diagnosis result (mark as valid or invalid)
     */
    @PostMapping("/validate")
    public ResponseEntity<Diagnosis> validateDiagnosis(
            @Valid @RequestBody ValidationRequest request) {
        Diagnosis result = diagnosisService.validateDiagnosis(request);
        return ResponseEntity.ok(result);
    }


    @GetMapping
    public List<Diagnosis> all() {
        return diagnosisService.findAll();
    }
}
