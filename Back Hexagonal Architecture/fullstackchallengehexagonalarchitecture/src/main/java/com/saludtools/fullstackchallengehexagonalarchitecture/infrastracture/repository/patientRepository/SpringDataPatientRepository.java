package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.patientRepository;

import com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.GenderEnumEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataPatientRepository extends JpaRepository<PatientEntity,Long> {

    @Query("SELECT p " +
            "FROM PatientEntity p " +
            "WHERE p.enabled = true")
    public Page<PatientEntity> findAllPatients(Pageable pageable);

    @Query("SELECT p " +
            "FROM PatientEntity p " +
            "WHERE p.enabled = true " +
            "AND (LOWER(p.name) LIKE LOWER(:name) " +
            "OR LOWER(p.lastName) LIKE LOWER(:lastName)) ")
    public Page<PatientEntity> findAllPatientWithFilterName(@Param("name") String name,
                                                            @Param("lastName") String lastName, Pageable pageable);

    @Query("SELECT p " +
            "FROM PatientEntity p " +
            "WHERE p.enabled = true " +
            "AND p.gender = :gender " +
            "AND (LOWER(p.name) LIKE LOWER(:name) " +
            "OR LOWER(p.lastName) LIKE LOWER(:lastName)) ")
    public Page<PatientEntity> findAllPatientWithFilterGender(@Param("name") String name,
                                                              @Param("lastName") String lastName,
                                                              @Param("gender") GenderEnumEntity gender, Pageable pageable);

    @Query("SELECT COUNT(p) " +
            "FROM PrescriptionEntity p " +
            "LEFT JOIN PatientEntity u ON p.patient.id = u.id " +
            "WHERE u.id = :userId " +
            "AND year(p.datePrescription) = year(CURRENT_DATE) " +
            "AND month(p.datePrescription) = month(CURRENT_DATE)")
    Integer countPrescriptionByCurrentMonthAndYear(@Param("userId")Long userId);

    @Query("SELECT p " +
            "FROM PatientEntity p " +
            "WHERE p.id = :userId " +
            "AND p.enabled = true")
    Optional<PatientEntity> findByIdAndEnabled(@Param("userId")Long userId);

    List<PatientEntity> findTop10ByNameContainsOrLastNameContainsAllIgnoreCase(String name, String lastName);

    @Query("SELECT p FROM PatientEntity p " +
            "WHERE p.name LIKE :name " +
            "OR p.lastName LIKE :lastName")
    List<PatientEntity> findByTerm(
            @Param("name") String name,
            @Param("lastName") String lastName);
}
