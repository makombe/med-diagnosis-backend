
package com.communityhealth.integration;

import org.springframework.stereotype.Component;

@Component
public class ApiMedicClient {

    public String fetchDiagnosis(String symptoms) {
        // Real implementation would authenticate and call ApiMedic API
        return "ApiMedic diagnosis result for symptoms: " + symptoms;
    }
}
