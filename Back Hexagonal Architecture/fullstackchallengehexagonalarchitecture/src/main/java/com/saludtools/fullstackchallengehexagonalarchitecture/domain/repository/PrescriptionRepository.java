package com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrescriptionRepository {

    Integer countPrescriptionByCurrentMonthAndYear(Long userId);

    Integer countPrescriptionByCurrentMonthAndMedicamentId(Long userId, Long medicamentId);

    Page<Prescription> findByUser(Long patientId, Pageable pageable);

    Prescription save(Prescription prescription);
    
}
