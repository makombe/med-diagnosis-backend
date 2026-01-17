package com.communityhealth.diagnosis;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class ApiMedicDiagnosisProvider implements DiagnosisProvider{

    @Override
    public List<Map<String, Object>> diagnose(
            String symptoms,
            LocalDate dateOfBirth,
            String gender) {

        // Authenticate
        // Call ApiMedic /diagnosis endpoint
        return List.of(); // real response
    }

}
