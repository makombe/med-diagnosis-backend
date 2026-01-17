
package com.communityhealth.controller;

import com.communityhealth.model.Diagnosis;
import com.communityhealth.service.DiagnosisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {

    private final DiagnosisService service;

    public DiagnosisController(DiagnosisService service) {
        this.service = service;
    }

    @PostMapping
    public Diagnosis diagnose(@RequestBody Diagnosis diagnosis) {
        return service.createDiagnosis(diagnosis);
    }

    @PostMapping("/{id}/validate")
    public Diagnosis validate(@PathVariable Long id, @RequestParam boolean valid) {
        return service.validateDiagnosis(id, valid);
    }

    @GetMapping
    public List<Diagnosis> all() {
        return service.findAll();
    }
}
