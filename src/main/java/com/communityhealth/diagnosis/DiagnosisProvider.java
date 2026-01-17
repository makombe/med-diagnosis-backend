package com.communityhealth.diagnosis;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DiagnosisProvider {
    List<Map<String, Object>> diagnose(
            String symptoms,
            LocalDate dateOfBirth,
            String gender
    );
}
