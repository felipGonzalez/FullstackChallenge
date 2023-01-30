package com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;

import java.util.List;
import java.util.Optional;

public interface MedicamentRepository{

    List<Medicament> findTop20ByNameContains(String name);

    List<Medicament> findByTerm(String name);

    List<Medicament> findAll();

    Optional<Medicament> findById(Long id);
}
