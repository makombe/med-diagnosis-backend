package com.communityhealth.diagnosis;

import com.communityhealth.integration.ApiMedicClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class ApiMedicDiagnosisProvider implements DiagnosisProvider{
    private final ApiMedicClient client;
    public ApiMedicDiagnosisProvider(ApiMedicClient client) {
        this.client = client;
    }

    @Override
    public List<Map<String, Object>> diagnose(
            String symptoms,
            LocalDate dateOfBirth,
            String gender) {
        String normalizedGender = gender.toLowerCase().startsWith("f") ? "female" : "male";

        return client.fetchDiagnosis(symptoms, dateOfBirth, normalizedGender);
    }

}
