package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;

import java.util.List;

public interface MedicamentService {

    public List<Medicament> getAllMedicament();

    public List<Medicament> searchByName(String text);
}
