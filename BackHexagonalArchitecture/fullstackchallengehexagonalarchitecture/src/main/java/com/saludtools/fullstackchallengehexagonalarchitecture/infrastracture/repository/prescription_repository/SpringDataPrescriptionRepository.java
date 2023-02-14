package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.prescription_repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPrescriptionRepository extends JpaRepository<PrescriptionEntity,Long> {

    @Query("SELECT COUNT(p) " +
            "FROM PrescriptionEntity p " +
            "LEFT JOIN PatientEntity u ON p.patient.id = u.id " +
            "WHERE u.id = :userId " +
            "AND year(p.datePrescription) = year(CURRENT_DATE) " +
            "AND month(p.datePrescription) = month(CURRENT_DATE)")
    Integer countPrescriptionByCurrentMonthAndYear(@Param("userId")Long userId);

    @Query("SELECT COUNT(p) " +
            "FROM PrescriptionEntity p " +
            "WHERE p.patient.id = :userId " +
            "AND p.medicament.id = :medicamentId " +
            "AND year(p.datePrescription) = year(CURRENT_DATE) " +
            "AND month(p.datePrescription) = month(CURRENT_DATE) ")
    Integer countPrescriptionByCurrentMonthAndMedicamentId(@Param("userId")Long userId,
                                                           @Param("medicamentId") Long medicamentId);

    @Query("SELECT p " +
            "FROM PrescriptionEntity p " +
            "LEFT JOIN PatientEntity u ON p.patient.id = u.id " +
            "WHERE u.id = :patientId " +
            "Order by p.datePrescription desc")
    Page<PrescriptionEntity> findByUser(@Param("patientId")Long patientId, Pageable pageable);
}
