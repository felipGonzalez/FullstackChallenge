package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Prescription;
import org.springframework.data.domain.Page;

public interface PrescriptionService {

    public Prescription createPrescription(Long patientId, Long medicamentId);

    public Page<Prescription> getPagePrescriptionByPatient(Long patientId, int page, int size);
}
