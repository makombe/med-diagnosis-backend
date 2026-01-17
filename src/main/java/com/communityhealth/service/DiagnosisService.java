
package com.communityhealth.service;

import com.communityhealth.diagnosis.DiagnosisProvider;
import com.communityhealth.dto.ValidationRequest;
import com.communityhealth.integration.ApiMedicClient;
import com.communityhealth.model.Diagnosis;
import com.communityhealth.repository.DiagnosisRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosisService {

    private final DiagnosisRepository repository;
    private final DiagnosisProvider diagnosisProvider;

    public DiagnosisService(DiagnosisRepository repository, DiagnosisProvider diagnosisProvider) {
        this.repository = repository;
        this.diagnosisProvider = diagnosisProvider;
    }

    @Transactional
    public List<Diagnosis> performDiagnosis(@Valid Diagnosis request) {

        List<Map<String, Object>> apiResults =
                diagnosisProvider.diagnose(
                        request.getSymptoms(),
                        request.getPatient().getDateOfBirth(),
                        request.getPatient().getGender()
                );

        List<Diagnosis> results = new ArrayList<>();

        for (Map<String, Object> result : apiResults) {
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setSymptoms(request.getSymptoms());

            Map<String, Object> issue = (Map<String, Object>) result.get("Issue");
            diagnosis.setDiagnosisName(issue.get("Name").toString());
            diagnosis.setIcd(issue.get("ICD").toString());
            diagnosis.setIcdName(issue.get("ICDName").toString());
            diagnosis.setValidationStatus("PENDING");

            results.add(repository.save(diagnosis));
        }

        return results;
    }

    @Transactional
    public Diagnosis validateDiagnosis(ValidationRequest request) {
        Diagnosis diagnosis = repository.findById(request.getDiagnosisId()).orElseThrow(() -> new RuntimeException("Diagnosis not found"));
        diagnosis.setValidationStatus(request.getStatus());
        return repository.save(diagnosis);
    }

    public List<Diagnosis> findAll() {
        return repository.findAll();
    }
}
