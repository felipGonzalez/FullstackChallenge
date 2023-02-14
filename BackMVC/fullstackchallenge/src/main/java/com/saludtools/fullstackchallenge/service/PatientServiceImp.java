package com.saludtools.fullstackchallenge.service;

import com.saludtools.fullstackchallenge.domain.dto.PatientDTO;
import com.saludtools.fullstackchallenge.domain.entity.Gender;
import com.saludtools.fullstackchallenge.dto_factory.PatientDtoFactory;
import com.saludtools.fullstackchallenge.repository.PatientRepository;
import com.saludtools.fullstackchallenge.domain.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service(value = "patientServiceImp")
@Transactional
public class PatientServiceImp implements PatientService {


    public static final String PATIENT_NOT_FOUND = "Paciente con id %d no encontrado";
    private PatientRepository patientRepository;
    private PatientDtoFactory patientDtoFactory;

    @Autowired
    public PatientServiceImp(PatientRepository patientRepository, PatientDtoFactory patientDtoFactory) {
        this.patientRepository = patientRepository;
        this.patientDtoFactory = patientDtoFactory;
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = patientDtoFactory.convertDtoToEntity(patientDTO);
        patient.setEnabled(true);
        patientRepository.save(patient);
        return patientDtoFactory.convertEntityToDTO(patient);
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO) {
        Patient patient = patientDtoFactory.convertDtoToEntity(patientDTO);
        Optional<Patient> patientOptional = patientRepository.findByIdAndEnabled(patientDTO.getId());
        if(!patientOptional.isPresent()) {
            throw new IllegalArgumentException(String.format(PATIENT_NOT_FOUND,patientDTO.getId()));
        }
        patient.setId(patientOptional.get().getId());
        patient.setEnabled(patientOptional.get().getEnabled());
        return patientDtoFactory.convertEntityToDTO(patientRepository.save(patient));
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
    public Page<PatientDTO> getAllPatient(Integer page, Integer size, String text, String gender) {
        Page<PatientDTO> patientDTOPage = checkFilter(page,size,text,gender);
        patientDTOPage.stream().forEach( p
                -> p.setIsPrescriptionAllowed(patientRepository.countPrescriptionByCurrentMonthAndYear(p.getId()) < 3));
        return patientDTOPage;
    }

    private Page<PatientDTO> checkFilter(Integer page, Integer size, String text, String gender){
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
    public PatientDTO findPatient(Long patientId){
        Optional<Patient> patientOptional = patientRepository.findByIdAndEnabled(patientId);
        if(!patientOptional.isPresent()){
            throw new IllegalArgumentException(String.format(PATIENT_NOT_FOUND,patientId));
        }
        return patientDtoFactory.convertEntityToDTO(patientOptional.get());
    }

    @Override
    public List<PatientDTO> searchByNameOrLastName(String text){
        if(text.length() <= 2){
            return patientDtoFactory.convertEntityToDTO(patientRepository.
                    findTop10ByNameContainsOrLastNameContainsAllIgnoreCase(text,text));
        }
        text = "%"+text.trim().replaceAll("\\s", "%")+"%";
        return patientDtoFactory.convertEntityToDTO(patientRepository.findByTerm(text,text));
    }
}
