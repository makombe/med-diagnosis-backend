package com.communityhealth.controller;

import com.communityhealth.dto.PatientSearchResponseDto;
import com.communityhealth.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<PatientSearchResponseDto> search(@RequestParam Map<String, String> params) {
        String searchTerm = params.getOrDefault("query", params.getOrDefault("q", params.get("name")));
        if (searchTerm == null || searchTerm.isBlank()) {
            throw new IllegalArgumentException("Search parameter is required");
        }

        return service.search(searchTerm)
                .stream()
                .map(p -> new PatientSearchResponseDto(p.getId(), p.getLastName(), p.getFirstName(), p.getGender(), p.getDateOfBirth()))
                .toList();
    }
}
