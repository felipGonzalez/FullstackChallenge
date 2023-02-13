package com.saludtools.fullstackchallenge.Controller;

import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionDTO;
import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallenge.Service.PrescriptionService;
import com.saludtools.fullstackchallenge.Domain.entity.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
@CrossOrigin(value = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping()
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionDTO prescriptionDTO){
        return  ResponseEntity.ok(prescriptionService
                .createPrescription(prescriptionDTO));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PrescriptionResponseDTO>> getAllPrescriptionByPatientPage(@RequestParam Long patientId, @RequestParam int page, @RequestParam int size){
        return  ResponseEntity.ok(prescriptionService.getPagePrescriptionByPatient(patientId,page,size));
    }
}
