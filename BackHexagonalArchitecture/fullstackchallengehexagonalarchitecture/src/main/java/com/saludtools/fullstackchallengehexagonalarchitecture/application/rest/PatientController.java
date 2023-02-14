package com.saludtools.fullstackchallengehexagonalarchitecture.application.rest;

import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto.PatientDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto_factory.PatientDtoFactory;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@CrossOrigin(value = "*")
public class PatientController {

    private final PatientService patientService;
    private final PatientDtoFactory patientDtoFactory;

    @Autowired
    public PatientController(PatientService patientService, PatientDtoFactory patientDtoFactory) {
        this.patientService = patientService;
        this.patientDtoFactory = patientDtoFactory;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id){
        return  ResponseEntity.ok(patientService.findPatient(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO){
        Patient patient = patientDtoFactory.convertDtoToEntity(patientDTO);
        patient = patientService.createPatient(patient);
        return  ResponseEntity.ok(patientDtoFactory.convertEntityToDTO(patient));
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO){
        Patient patient = patientDtoFactory.convertDtoToEntity(patientDTO);
        patient = patientService.updatePatient(patient, patientDTO.getId());
        return  ResponseEntity.ok(patientDtoFactory.convertEntityToDTO(patient));
    }

    @DeleteMapping()
    public ResponseEntity deletePatient(@RequestParam Long idUser){
        patientService.deletePatient(idUser);
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PatientDTO>> getAllPatientPage(@RequestParam int page, @RequestParam int size,
                                                              @RequestParam(defaultValue = "") String text,
                                                              @RequestParam(defaultValue = "") String gender){
        Page<Patient> patientPage = patientService.getAllPatient(page,size,text,gender);
        return  ResponseEntity.ok(patientDtoFactory.convertPageEntityToDTO(patientPage));
    }

    @GetMapping()
    public ResponseEntity<List<PatientDTO>> searchPatients(
            @RequestParam(value = "textToSearch", required = false) String textToSearch){
        List<Patient> patientList = patientService.searchByNameOrLastName(textToSearch);
        return ResponseEntity.ok(patientDtoFactory.convertEntityToDTO(patientList));
    }
}
