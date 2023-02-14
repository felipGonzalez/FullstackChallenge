package com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    public Page<Patient> findAllPatients(Pageable pageable);

    public Page<Patient> findAllPatientWithFilterName(String name,String lastName, Pageable pageable);

    public Page<Patient> findAllPatientWithFilterGender(String name,String lastName,Gender gender, Pageable pageable);

    Integer countPrescriptionByCurrentMonthAndYear(Long userId);

    Optional<Patient> findByIdAndEnabled(Long userId);

    List<Patient> findTop10ByNameContainsOrLastNameContainsAllIgnoreCase(String name, String lastName);

    List<Patient> findByTerm(String name,String lastName);

    public Patient save(Patient patient);
}
