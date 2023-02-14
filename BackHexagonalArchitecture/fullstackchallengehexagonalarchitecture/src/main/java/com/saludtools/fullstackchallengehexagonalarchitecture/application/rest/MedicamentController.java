package com.saludtools.fullstackchallengehexagonalarchitecture.application.rest;

import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto.MedicamentDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto_factory.MedicamentDTOFactory;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.service.MedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicament")
@CrossOrigin(value = "*")
public class MedicamentController {

    private final MedicamentService medicamentService;
    private final MedicamentDTOFactory medicamentDTOFactory;

    @Autowired
    public MedicamentController(MedicamentService medicamentService, MedicamentDTOFactory medicamentDTOFactory) {
        this.medicamentService = medicamentService;
        this.medicamentDTOFactory = medicamentDTOFactory;
    }

    @GetMapping()
    public ResponseEntity<List<MedicamentDTO>> searchMedicaments(
            @RequestParam(value = "textToSearch", required = false, defaultValue = "") String textToSearch){
        return ResponseEntity.ok(medicamentDTOFactory
                .convertEntityToDTO(medicamentService.searchByName(textToSearch)));
    }
}
