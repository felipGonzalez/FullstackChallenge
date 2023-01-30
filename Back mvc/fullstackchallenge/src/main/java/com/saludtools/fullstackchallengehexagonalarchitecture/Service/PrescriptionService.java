package com.saludtools.fullstackchallengehexagonalarchitecture.Service;

import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.dto.PrescriptionDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.entity.Prescription;
import org.springframework.data.domain.Page;

public interface PrescriptionService {

    public Prescription createPrescription(PrescriptionDTO prescriptionDTO);

    public Page<PrescriptionResponseDTO> getPagePrescriptionByPatient(Long patientId, int page, int size);

}
