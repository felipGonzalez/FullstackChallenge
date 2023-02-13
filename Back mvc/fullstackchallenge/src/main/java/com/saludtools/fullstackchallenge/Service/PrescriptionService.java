package com.saludtools.fullstackchallenge.Service;

import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionDTO;
import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallenge.Domain.entity.Prescription;
import org.springframework.data.domain.Page;

public interface PrescriptionService {

    public Prescription createPrescription(PrescriptionDTO prescriptionDTO);

    public Page<PrescriptionResponseDTO> getPagePrescriptionByPatient(Long patientId, int page, int size);

}
