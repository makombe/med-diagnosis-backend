package com.communityhealth.service;

import com.communityhealth.dto.PatientDto;
import com.communityhealth.mapper.PatientMapper;
import com.communityhealth.model.Patient;
import com.communityhealth.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;
    public List<Patient> search(String query) {
        return repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
    }

    public PatientDto create( PatientDto dto) {
        Patient patient = mapper.toEntity(dto);
        return mapper.toDto(repository.save(patient));
    }
}



