package com.saludtools.fullstackchallenge.Service;

import com.saludtools.fullstackchallenge.Domain.dto.PatientDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {

    public PatientDTO createPatient(PatientDTO patient);

    public void deletePatient(Long id);

    public Page<PatientDTO> getAllPatient(Integer page, Integer size,String text, String gender);

    public PatientDTO findPatient(Long patientId);

    public PatientDTO updatePatient(PatientDTO patientDTO);

    public List<PatientDTO> searchByNameOrLastName(String text);

}
