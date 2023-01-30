package com.saludtools.fullstackchallengehexagonalarchitecture.Service;

import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.dto.MedicamentDTO;

import java.util.List;

public interface MedicamentService {

    public List<MedicamentDTO> getAllMedicament();

    public List<MedicamentDTO> searchByName(String text);
}
