package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.medicament_repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MedicamentEntityRepositoryImp implements MedicamentRepository {

    private final SpringDataMedicamentRepository medicamentRepository;

    @Autowired
    public MedicamentEntityRepositoryImp(SpringDataMedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    public List<Medicament> findTop20ByNameContains(String name) {
        List<MedicamentEntity> medicamentEntities = medicamentRepository.findTop20ByNameContains(name);
        return medicamentEntities.stream().map(m -> m.toMedicament()).collect(Collectors.toList());
    }

    @Override
    public List<Medicament> findByTerm(String name) {
        List<MedicamentEntity> medicamentEntities = medicamentRepository.findTop20ByNameContains(name);
        return medicamentEntities
                .stream()
                .map(m -> m.toMedicament())
                .collect(Collectors.toList());
    }

    @Override
    public List<Medicament> findAll() {
        return medicamentRepository
                .findAll()
                .stream()
                .map(m -> m.toMedicament())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Medicament> findById(Long id){
        Optional<MedicamentEntity> medicamentEntityOptional = medicamentRepository.findById(id);
        if(medicamentEntityOptional.isPresent()) {
            return Optional.ofNullable(medicamentEntityOptional.get().toMedicament());
        }
        return Optional.empty();
    }
}
