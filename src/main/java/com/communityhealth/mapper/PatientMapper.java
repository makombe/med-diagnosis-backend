package com.communityhealth.mapper;

import com.communityhealth.dto.PatientDto;
import com.communityhealth.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(PatientDto dto);

    PatientDto toDto(Patient patient);

    void updateEntityFromDto(PatientDto dto, @MappingTarget Patient patient);
}