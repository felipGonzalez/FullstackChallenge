package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.MedicamentRepository;

import java.util.List;

public class MedicamentServiceImp implements MedicamentService{

    private final MedicamentRepository medicamentRepository;

    public MedicamentServiceImp(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    public List<Medicament> getAllMedicament() {
        return medicamentRepository.findAll();
    }

    @Override
    public List<Medicament> searchByName(String text) {
        if(text == null || text.length() <= 2){
            return medicamentRepository.findTop20ByNameContains(text);
        }
        text = "%"+text.trim().replaceAll("\\s", "%")+"%";
        return medicamentRepository.findByTerm(text);
    }
}
