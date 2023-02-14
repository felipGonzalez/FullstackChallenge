package com.saludtools.fullstackchallenge.service;

import com.saludtools.fullstackchallenge.domain.dto.PrescriptionDTO;
import com.saludtools.fullstackchallenge.domain.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallenge.domain.entity.Prescription;
import org.springframework.data.domain.Page;

public interface PrescriptionService {

    public Prescription createPrescription(PrescriptionDTO prescriptionDTO);

    public Page<PrescriptionResponseDTO> getPagePrescriptionByPatient(Long patientId, int page, int size);

}
