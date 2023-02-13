package com.saludtools.fullstackchallenge.DtoFactory;

import com.saludtools.fullstackchallenge.Builder.PatientBuilder;
import com.saludtools.fullstackchallenge.Builder.PatientDTOBuilder;
import com.saludtools.fullstackchallenge.Domain.dto.PatientDTO;
import com.saludtools.fullstackchallenge.Domain.entity.Patient;
import com.saludtools.fullstackchallenge.Repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;

@Component
public class PatientDtoFactory {

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    public PatientDTO convertEntityToDTO(Patient patient){
        if(isNull(patient)) return new PatientDTO();
        PatientDTO patientDTO = new PatientDTOBuilder()
                .withId(patient.getId())
                .withBirthDate(patient.getBirthDate())
                .withName(patient.getName())
                .withLastName(patient.getLastName())
                .withGender(patient.getGender())
                .build();
        patientDTO.setIsPrescriptionAllowed(prescriptionRepository.countPrescriptionByCurrentMonthAndYear(patient.getId()) > 2);
        return patientDTO;
    }

    public Patient convertDtoToEntity(PatientDTO patientDTO){
        return new PatientBuilder()
                .withBirthDate(patientDTO.getBirthDate())
                .withName(patientDTO.getName())
                .withLastName(patientDTO.getLastName())
                .withGender(patientDTO.getGender())
                .build();
    }

    public List<PatientDTO> convertEntityToDTO(List<Patient> patients) {
        List<PatientDTO> response = new ArrayList<>();
        for (Patient patient : patients) {
            response.add(convertEntityToDTO(patient));
        }
        return response;
    }
}