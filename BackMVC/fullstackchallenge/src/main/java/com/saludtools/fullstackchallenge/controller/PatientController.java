package com.saludtools.fullstackchallenge.controller;

import com.saludtools.fullstackchallenge.domain.dto.PatientDTO;
import com.saludtools.fullstackchallenge.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin(value = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id){
        return  ResponseEntity.ok(patientService.findPatient(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO){
        return  ResponseEntity.ok(patientService.createPatient(patientDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO){
        return  ResponseEntity.ok(patientService.updatePatient(patientDTO));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PatientDTO>> getAllPatientPage(@RequestParam int page, @RequestParam int size,
                                                              @RequestParam(defaultValue = "") String text,
                                                              @RequestParam(defaultValue = "") String gender){
        return  ResponseEntity.ok(patientService.getAllPatient(page,size,text,gender));
    }

    @DeleteMapping()
    public ResponseEntity deletePatient(@RequestParam Long idUser){
        patientService.deletePatient(idUser);
        return  ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<PatientDTO>> searchPatients(
            @RequestParam(value = "textToSearch", required = false) String textToSearch){
        return ResponseEntity.ok(patientService.searchByNameOrLastName(textToSearch));
    }

}
