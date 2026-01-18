package com.communityhealth.controller;

import com.communityhealth.dto.PatientDto;
import com.communityhealth.dto.PatientSearchResponseDto;
import com.communityhealth.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Create patient
     */
    @PostMapping
    public ResponseEntity<PatientDto> create(
            @Valid @RequestBody PatientDto request) {
        return ResponseEntity.ok(patientService.create(request));
    }

    @GetMapping("/search")
    public List<PatientSearchResponseDto> search(@RequestParam Map<String, String> params) {
        String searchTerm = params.getOrDefault("query", params.getOrDefault("q", params.get("name")));
        if (searchTerm == null || searchTerm.isBlank()) {
            throw new IllegalArgumentException("Search parameter is required");
        }

        return patientService.search(searchTerm)
                .stream()
                .map(p -> new PatientSearchResponseDto(p.getId(), p.getLastName(), p.getFirstName(), p.getGender(), p.getDateOfBirth()))
                .toList();
    }
    //TODO add end point for updating patient details
}
