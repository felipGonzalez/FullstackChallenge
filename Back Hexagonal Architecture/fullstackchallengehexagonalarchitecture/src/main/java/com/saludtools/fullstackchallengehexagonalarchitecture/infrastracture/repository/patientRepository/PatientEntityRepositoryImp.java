package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.patientRepository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PatientRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.GenderEnumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PatientEntityRepositoryImp implements PatientRepository {
    private final SpringDataPatientRepository patientRepository;

    @Autowired
    public PatientEntityRepositoryImp(SpringDataPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Page<Patient> findAllPatients(Pageable pageable) {
        Page<PatientEntity> patientEntities=  this.patientRepository.findAllPatients(pageable);
        return patientEntities.map(new Function<PatientEntity, Patient>() {
            @Override
            public Patient apply(PatientEntity patientEntity) {
                Patient patient = patientEntity.toPatient();
                patient.setIsPrescriptionAllowed(patientRepository.countPrescriptionByCurrentMonthAndYear(patient.getId()) < 3);
                return patient;
            }
        });
    }

    @Override
    public Page<Patient> findAllPatientWithFilterName(String name, String lastName, Pageable pageable) {
        Page<PatientEntity> patientEntities=  this.patientRepository
                .findAllPatientWithFilterName(name,lastName,pageable);
        return patientEntities.map(new Function<PatientEntity, Patient>() {
            @Override
            public Patient apply(PatientEntity patientEntity) {
                Patient patient = patientEntity.toPatient();
                patient.setIsPrescriptionAllowed(patientRepository.countPrescriptionByCurrentMonthAndYear(patient.getId()) < 3);
                return patient;
            }
        });
    }

    @Override
    public Page<Patient> findAllPatientWithFilterGender(String name, String lastName, Gender gender, Pageable pageable) {
        Page<PatientEntity> patientEntities=  this.patientRepository
                .findAllPatientWithFilterGender(name,lastName, GenderEnumEntity.valueOf(gender.getValue()),pageable);
        return patientEntities.map(new Function<PatientEntity, Patient>() {
            @Override
            public Patient apply(PatientEntity patientEntity) {
                Patient patient = patientEntity.toPatient();
                patient.setIsPrescriptionAllowed(patientRepository.countPrescriptionByCurrentMonthAndYear(patient.getId()) < 3);
                return patient;
            }
        });
    }

    @Override
    public Integer countPrescriptionByCurrentMonthAndYear(Long userId) {
        return patientRepository.countPrescriptionByCurrentMonthAndYear(userId);
    }

    @Override
    public Optional<Patient> findByIdAndEnabled(Long userId) {
        Optional<PatientEntity> patientEntity = patientRepository.findByIdAndEnabled(userId);
        if (patientEntity.isPresent()) {
            return Optional.of(patientEntity.get().toPatient());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Patient> findTop10ByNameContainsOrLastNameContainsAllIgnoreCase(String name, String lastName) {
        List<PatientEntity> patientEntities = patientRepository.findTop10ByNameContainsOrLastNameContainsAllIgnoreCase(name,lastName);
        return patientEntities
                .stream()
                .map( patientEntity -> patientEntity.toPatient())
                .collect(Collectors.toList());
    }

    @Override
    public List<Patient> findByTerm(String name, String lastName) {
        List<PatientEntity> patientEntities = patientRepository.findByTerm(name,lastName);
        return patientEntities
                .stream()
                .map( patientEntity -> patientEntity.toPatient())
                .collect(Collectors.toList());
    }

    @Override
    public Patient save(Patient patient){
        return patientRepository.save(new PatientEntity(patient)).toPatient();
    }
}
