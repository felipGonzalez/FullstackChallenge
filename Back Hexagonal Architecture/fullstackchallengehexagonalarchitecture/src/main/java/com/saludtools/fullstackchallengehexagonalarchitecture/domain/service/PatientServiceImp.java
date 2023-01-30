package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.DomainException;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PatientRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.validator.PatientValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class PatientServiceImp implements PatientService {

    public static final String PATIENT_NOT_FOUND = "Paciente con id %d no encontrado";
    private final PatientRepository patientRepository;

    public PatientServiceImp(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient createPatient(Patient patient) {
        PatientValidation.validateDataForCreate(patient);
        patient.setEnabled(true);
        patient = patientRepository.save(patient);
        return patient;
    }

    @Override
    public Patient updatePatient(Patient patient, Long id) {
        PatientValidation.validateId(id);
        Optional<Patient> patientOptional = patientRepository.findByIdAndEnabled(id);
        if(!patientOptional.isPresent()) {
            throw new IllegalArgumentException(String.format(PATIENT_NOT_FOUND,id));
        }
        patient.setId(patientOptional.get().getId());
        patient.setEnabled(patientOptional.get().getEnabled());
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        Optional<Patient> patientOptional = patientRepository.findByIdAndEnabled(id);
        if(!patientOptional.isPresent()) {
            throw new IllegalArgumentException(String.format(PATIENT_NOT_FOUND,id));
        }
        Patient patient = patientOptional.get();
        patient.setEnabled(false);
        patientRepository.save(patient);
    }

    @Override
    public Page<Patient> getAllPatient(Integer page, Integer size, String text, String gender) {
        return checkFilter(page,size,text,gender);
    }

    private Page<Patient> checkFilter(Integer page, Integer size, String text, String gender){
        Pageable pageable = PageRequest.of(page, size);
        if(text.isEmpty() && gender.isEmpty()){
            return patientRepository.findAllPatients(pageable);
        }
        text = "%"+text.trim().replaceAll("\\s", "%")+"%";
        if(!text.isEmpty() && gender.isEmpty()){
            return patientRepository.findAllPatientWithFilterName(text,text,pageable);
        }
        Gender gender1 = Gender.findByValue(gender);
        return  patientRepository.findAllPatientWithFilterGender(text,text,gender1,pageable);

    }

    @Override
    public Patient findPatient(Long patientId){
        Optional<Patient> patientOptional = patientRepository.findByIdAndEnabled(patientId);
        if(!patientOptional.isPresent()){
            throw new DomainException(String.format(PATIENT_NOT_FOUND,patientId));
        }
        Patient patient = patientOptional.get();
        patient.setIsPrescriptionAllowed(patientRepository.countPrescriptionByCurrentMonthAndYear(patientId) < 3);
        return patientOptional.get();
    }

    @Override
    public List<Patient> searchByNameOrLastName(String text){
        if(text.length() <= 2){
            return patientRepository.
                    findTop10ByNameContainsOrLastNameContainsAllIgnoreCase(text,text);
        }
        text = "%"+text.trim().replaceAll("\\s", "%")+"%";
        return patientRepository.findByTerm(text,text);
    }
}
