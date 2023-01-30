package com.saludtools.fullstackchallengehexagonalarchitecture.Repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "prescriptionRepository")
@Transactional
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {

    @Query("SELECT COUNT(p) " +
            "FROM Prescription p " +
            "LEFT JOIN Patient u ON p.patient.id = u.id " +
            "WHERE u.id = :userId " +
            "AND year(p.datePrescription) = year(CURRENT_DATE) " +
            "AND month(p.datePrescription) = month(CURRENT_DATE)")
    Integer countPrescriptionByCurrentMonthAndYear(@Param("userId")Long userId);

    @Query("SELECT COUNT(p) " +
            "FROM Prescription p " +
            "WHERE p.patient.id = :userId " +
            "AND p.medicament.id = :medicamentId " +
            "AND year(p.datePrescription) = year(CURRENT_DATE) " +
            "AND month(p.datePrescription) = month(CURRENT_DATE) ")
    Integer countPrescriptionByCurrentMonthAndMedicamentId(@Param("userId")Long userId,
                                                           @Param("medicamentId") Long medicamentId);

    @Query("SELECT p " +
            "FROM Prescription p " +
            "LEFT JOIN Patient u ON p.patient.id = u.id " +
            "WHERE u.id = :patientId " +
            "Order by p.datePrescription desc")
    Page<Prescription> findByUser(@Param("patientId")Long patientId, Pageable pageable);
    
}
