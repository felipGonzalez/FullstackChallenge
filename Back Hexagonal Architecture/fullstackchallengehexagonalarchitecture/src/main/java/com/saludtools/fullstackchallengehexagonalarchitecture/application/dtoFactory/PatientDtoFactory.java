package com.saludtools.fullstackchallengehexagonalarchitecture.application.dtoFactory;

import com.saludtools.fullstackchallengehexagonalarchitecture.application.builder.PatientBuilder;
import com.saludtools.fullstackchallengehexagonalarchitecture.application.builder.PatientDTOBuilder;
import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto.PatientDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.isNull;

@Component
public class PatientDtoFactory {

    public PatientDTO convertEntityToDTO(Patient patient){
        if(isNull(patient)) return new PatientDTO();
        PatientDTO patientDTO = new PatientDTOBuilder()
                .withId(patient.getId())
                .withBirthDate(patient.getBirthDate())
                .withName(patient.getName())
                .withLastName(patient.getLastName())
                .withGender(patient.getGender().getValue())
                .withIsPrescriptionAllowed(patient.getIsPrescriptionAllowed())
                .build();
        return patientDTO;
    }

    public Patient convertDtoToEntity(PatientDTO patientDTO){
        return new PatientBuilder()
                .withBirthDate(patientDTO.getBirthDate())
                .withName(patientDTO.getName())
                .withLastName(patientDTO.getLastName())
                .withGender(Gender.findByValue(patientDTO.getGender()))
                .build();
    }

    public List<PatientDTO> convertEntityToDTO(List<Patient> patients) {
        List<PatientDTO> response = new ArrayList<>();
        for (Patient patient : patients) {
            response.add(convertEntityToDTO(patient));
        }
        return response;
    }

    public Page<PatientDTO> convertPageEntityToDTO(Page<Patient> patientPage){
        return patientPage.map(new Function<Patient, PatientDTO>() {
            @Override
            public PatientDTO apply(Patient patient) {
                return convertEntityToDTO(patient);
            }
        });
    }
}
