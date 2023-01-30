package com.saludtools.fullstackchallengehexagonalarchitecture.Repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.dto.PatientDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.entity.Gender;
import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository(value = "patientRepository")
@Transactional
public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("SELECT new com.saludtools.fullstackchallenge.Domain.dto.PatientDTO(" +
            "p.id, " +
            "p.name, " +
            "p.lastName," +
            "p.birthDate," +
            "p.gender"+
            ") " +
            "FROM Patient p " +
            "WHERE p.enabled = true")
    public Page<PatientDTO> findAllPatients(Pageable pageable);

    @Query("SELECT new com.saludtools.fullstackchallenge.Domain.dto.PatientDTO(" +
            "p.id, " +
            "p.name, " +
            "p.lastName," +
            "p.birthDate," +
            "p.gender"+
            ") " +
            "FROM Patient p " +
            "WHERE p.enabled = true " +
            "AND (LOWER(p.name) LIKE LOWER(:name) " +
            "OR LOWER(p.lastName) LIKE LOWER(:lastName)) ")
    public Page<PatientDTO> findAllPatientWithFilterName(@Param("name") String name,
                                                     @Param("lastName") String lastName, Pageable pageable);

    @Query("SELECT new com.saludtools.fullstackchallenge.Domain.dto.PatientDTO(" +
            "p.id, " +
            "p.name, " +
            "p.lastName," +
            "p.birthDate," +
            "p.gender"+
            ") " +
            "FROM Patient p " +
            "WHERE p.enabled = true " +
            "AND p.gender = :gender " +
            "AND (LOWER(p.name) LIKE LOWER(:name) " +
            "OR LOWER(p.lastName) LIKE LOWER(:lastName)) ")
    public Page<PatientDTO> findAllPatientWithFilterGender(@Param("name") String name,
                                           @Param("lastName") String lastName,
                                           @Param("gender") Gender gender, Pageable pageable);

    @Query("SELECT COUNT(p) " +
            "FROM Prescription p " +
            "LEFT JOIN Patient u ON p.patient.id = u.id " +
            "WHERE u.id = :userId " +
            "AND year(p.datePrescription) = year(CURRENT_DATE) " +
            "AND month(p.datePrescription) = month(CURRENT_DATE)")
    Integer countPrescriptionByCurrentMonthAndYear(@Param("userId")Long userId);

    @Query("SELECT p " +
            "FROM Patient p " +
            "WHERE p.id = :userId " +
            "AND p.enabled = true")
    Optional<Patient> findByIdAndEnabled(@Param("userId")Long userId);

    List<Patient> findTop10ByNameContainsOrLastNameContainsAllIgnoreCase(String name, String lastName);

    @Query("SELECT p FROM Patient p " +
            "WHERE p.name LIKE :name " +
            "OR p.lastName LIKE :lastName")
    List<Patient> findByTerm(
            @Param("name") String name,
            @Param("lastName") String lastName);
}
