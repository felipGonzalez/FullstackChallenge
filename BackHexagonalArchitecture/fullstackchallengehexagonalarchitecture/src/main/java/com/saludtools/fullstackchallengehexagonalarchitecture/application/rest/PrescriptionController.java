package com.saludtools.fullstackchallengehexagonalarchitecture.application.rest;

import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto.PrescriptionDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto_factory.PrescriptionDtoFactory;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Prescription;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
@CrossOrigin(value = "*")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final PrescriptionDtoFactory prescriptionDtoFactory;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, PrescriptionDtoFactory prescriptionDtoFactory) {
        this.prescriptionService = prescriptionService;
        this.prescriptionDtoFactory = prescriptionDtoFactory;
    }

    @PostMapping()
    public ResponseEntity<PrescriptionResponseDTO> createPrescription(@RequestBody PrescriptionDTO prescriptionDTO){
        Prescription prescription = prescriptionService
                .createPrescription(prescriptionDTO.getPatientId(),prescriptionDTO.getMedicamentId());
        return  ResponseEntity.ok(prescriptionDtoFactory.convertEntityToDTO(prescription));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PrescriptionResponseDTO>> getAllPrescriptionByPatientPage(@RequestParam Long patientId, @RequestParam int page, @RequestParam int size){
        Page<Prescription> prescriptions = prescriptionService.getPagePrescriptionByPatient(patientId,page,size);
        return  ResponseEntity.ok(prescriptionDtoFactory.convertPageEntityToDTO(prescriptions));
    }
}
