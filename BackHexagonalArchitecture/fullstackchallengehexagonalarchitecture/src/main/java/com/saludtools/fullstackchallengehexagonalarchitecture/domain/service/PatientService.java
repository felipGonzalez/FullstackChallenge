package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PatientService {

    public Patient createPatient(Patient patient);

    public void deletePatient(Long id);

    public Page<Patient> getAllPatient(Integer page, Integer size,String text, String gender);

    public Patient findPatient(Long patientId);

    public Patient updatePatient(Patient patient,Long id);

    public List<Patient> searchByNameOrLastName(String text);

}
