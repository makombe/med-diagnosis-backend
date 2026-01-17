package com.communityhealth.controller;

import com.communityhealth.dto.PatientSearchResponseDto;
import com.communityhealth.service.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<PatientSearchResponseDto> search(@RequestParam String query) {

        return service.search(query)
                .stream()
                .map(p -> new PatientSearchResponseDto())
                .toList();
    }
}
