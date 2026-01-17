
package com.communityhealth.service;

import com.communityhealth.integration.ApiMedicClient;
import com.communityhealth.model.Diagnosis;
import com.communityhealth.repository.DiagnosisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisService {

    private final DiagnosisRepository repository;
    private final ApiMedicClient apiMedicClient;

    public DiagnosisService(DiagnosisRepository repository, ApiMedicClient apiMedicClient) {
        this.repository = repository;
        this.apiMedicClient = apiMedicClient;
    }

    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        String result = apiMedicClient.fetchDiagnosis(diagnosis.getSymptoms());
        diagnosis.setDiagnosisResult(result);
        diagnosis.setValid(false);
        return repository.save(diagnosis);
    }

    public Diagnosis validateDiagnosis(Long id, boolean valid) {
        Diagnosis d = repository.findById(id).orElseThrow();
        d.setValid(valid);
        return repository.save(d);
    }

    public List<Diagnosis> findAll() {
        return repository.findAll();
    }
}
