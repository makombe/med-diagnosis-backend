
package com.communityhealth.service;

import com.communityhealth.diagnosis.DiagnosisProvider;
import com.communityhealth.dto.DiagnosisRequestDto;
import com.communityhealth.dto.DiagnosisResponseDto;
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
    public List<DiagnosisResponseDto> performDiagnosis(@Valid DiagnosisRequestDto request) {

        var apiResults =
                diagnosisProvider.diagnose(
                        request.getSymptoms(),
                        request.getPatient().getDateOfBirth(),
                        request.getPatient().getGender()
                );
        if (apiResults.isEmpty()) {
            return List.of();
        }
        List<Diagnosis> entities = apiResults.stream()
                .map(r -> mapToEntity(r, request.getSymptoms()))
                .toList();

        // One transaction - batch save
        List<Diagnosis> saved = repository.saveAll(entities);

        return saved.stream()
                .map(this::toResponseDto)
                .toList();

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

    public DiagnosisResponseDto toResponseDto(Diagnosis entity) {
        return new DiagnosisResponseDto(
                entity.getId(),
                entity.getDiagnosisName(),
                entity.getIcd(),
                entity.getIcdName(),
                entity.getSymptoms(),
                entity.getValidationStatus(),
                entity.getCreatedAt()
        );
    }

    private Diagnosis mapToEntity(Map<String, Object> apiResult, String symptoms) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setSymptoms(symptoms);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> issue = (Map<String, Object>) apiResult.get("Issue");

            diagnosis.setDiagnosisName(getString(issue, "Name"));
            diagnosis.setIcd(getString(issue, "Icd"));
            diagnosis.setIcdName(getString(issue, "IcdName"));
        } catch (Exception e) {
           // throw new InvalidApiResponseException("Cannot parse ApiMedic diagnosis response", e);
        }

        diagnosis.setValidationStatus("PENDING");
        return diagnosis;
    }

    private static String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }
}
