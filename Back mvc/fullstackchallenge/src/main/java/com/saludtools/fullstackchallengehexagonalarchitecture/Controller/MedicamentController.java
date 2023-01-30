package com.saludtools.fullstackchallengehexagonalarchitecture.Controller;

import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.dto.MedicamentDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.Service.MedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicament")
@CrossOrigin(value = "*")
public class MedicamentController {

    @Autowired
    private MedicamentService medicamentService;

    @GetMapping()
    public ResponseEntity<List<MedicamentDTO>> searchMedicaments(
            @RequestParam(value = "textToSearch", required = false) String textToSearch){
        return ResponseEntity.ok(medicamentService.searchByName(textToSearch));
    }
}
