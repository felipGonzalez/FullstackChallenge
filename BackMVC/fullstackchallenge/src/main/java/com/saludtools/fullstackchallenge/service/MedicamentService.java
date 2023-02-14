package com.saludtools.fullstackchallenge.service;

import com.saludtools.fullstackchallenge.domain.dto.MedicamentDTO;

import java.util.List;

public interface MedicamentService {

    public List<MedicamentDTO> getAllMedicament();

    public List<MedicamentDTO> searchByName(String text);
}
