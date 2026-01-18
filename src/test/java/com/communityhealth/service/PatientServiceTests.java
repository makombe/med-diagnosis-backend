package com.communityhealth.service;

import com.communityhealth.dto.PatientDto;
import com.communityhealth.mapper.PatientMapper;
import com.communityhealth.model.Patient;
import com.communityhealth.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTests {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    private Patient testPatient;
    private PatientDto testPatientDto;

    @BeforeEach
    void setUp() {
        // Create reusable test objects
        testPatient = new Patient();
        testPatient.setId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");

        testPatientDto = new PatientDto();
        testPatientDto.setFirstName("John");
        testPatientDto.setLastName("Doe");
    }

    @Test
    @DisplayName("Should find patients by name search (case insensitive)")
    void shouldSearchPatientsByName() {
        // Arrange
        String searchQuery = "doe";
        List<Patient> patients = List.of(testPatient);

        when(patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                anyString(), anyString()))
                .thenReturn(patients);

        // Act
        List<Patient> result = patientService.search(searchQuery);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(0).getLastName()).isEqualTo("Doe");

        verify(patientRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchQuery, searchQuery);
    }

    @Test
    @DisplayName("Should return empty list when no patients match search query")
    void shouldReturnEmptyListWhenNoMatch() {
        // Arrange
        String searchQuery = "xyz";
        when(patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                anyString(), anyString()))
                .thenReturn(List.of());

        // Act
        List<Patient> result = patientService.search(searchQuery);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(patientRepository, times(1))
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchQuery, searchQuery);
    }

    @Test
    @DisplayName("Should create new patient and return DTO")
    void shouldCreatePatientSuccessfully() {
        // Arrange
        when(patientMapper.toEntity(any(PatientDto.class))).thenReturn(testPatient);
        when(patientRepository.save(any(Patient.class))).thenReturn(testPatient);
        when(patientMapper.toDto(any(Patient.class))).thenReturn(testPatientDto);
        // Act
        PatientDto result = patientService.create(testPatientDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        assertThat(result.getLastName()).isEqualTo("Doe");

        // Verify interactions
        verify(patientMapper).toEntity(testPatientDto);
        verify(patientRepository).save(testPatient);
        verify(patientMapper).toDto(testPatient);
    }
}
