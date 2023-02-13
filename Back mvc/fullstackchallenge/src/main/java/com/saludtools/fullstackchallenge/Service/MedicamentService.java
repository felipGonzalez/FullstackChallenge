package com.saludtools.fullstackchallenge.Service;

import com.saludtools.fullstackchallenge.Domain.dto.MedicamentDTO;

import java.util.List;

public interface MedicamentService {

    public List<MedicamentDTO> getAllMedicament();

    public List<MedicamentDTO> searchByName(String text);
}
