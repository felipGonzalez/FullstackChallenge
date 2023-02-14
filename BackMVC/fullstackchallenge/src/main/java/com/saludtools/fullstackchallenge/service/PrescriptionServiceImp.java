package com.saludtools.fullstackchallenge.service;

import com.saludtools.fullstackchallenge.domain.dto.PrescriptionDTO;
import com.saludtools.fullstackchallenge.domain.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallenge.dto_factory.PrescriptionDtoFactory;
import com.saludtools.fullstackchallenge.repository.MedicamentRepository;
import com.saludtools.fullstackchallenge.repository.PatientRepository;
import com.saludtools.fullstackchallenge.repository.PrescriptionRepository;
import com.saludtools.fullstackchallenge.domain.entity.Medicament;
import com.saludtools.fullstackchallenge.domain.entity.Patient;
import com.saludtools.fullstackchallenge.domain.entity.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import static  java.util.Objects.isNull;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

@Service(value = "prescriptionServiceImp")
@Transactional
public class PrescriptionServiceImp implements PrescriptionService{


    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private PrescriptionDtoFactory prescriptionDtoFactory;
    @Override
    public Prescription createPrescription(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = prescriptionDtoFactory.convertDtoToEntity(prescriptionDTO);
        Optional<Patient> patient = patientRepository.findById(prescription.getPatient().getId());
        Optional<Medicament> medicament = medicamentRepository.findById(prescription.getMedicament().getId());

        validatePrescription(patient.get(),medicament.get());

        prescription.setPatient(patient.get());
        prescription.setMedicament(medicament.get());

       return prescriptionRepository.save(prescription);
    }
    public void validatePrescription(Patient patient, Medicament medicament){
        checkGendePrescription(patient,medicament);
        checkNumberPrescriptions(patient.getId());
        verifyMinimumAgeDrug(patient,medicament);
        checkPrescriptionByCurrentMonthAndMedicamentId(patient,medicament);
    }

    private void checkGendePrescription(Patient patient, Medicament medicament){
        if (isNull(medicament.getExclusiveUse())) {
            return;
        }
        if(patient.getGender().equals(medicament.getExclusiveUse())) {
           return;
        }
        throw new IllegalArgumentException(String.format("El medicamento %s solo puede ser usado para usuarios de genero %s.",
                medicament.getName(),medicament.getExclusiveUse().getName()));

    }

    private void checkNumberPrescriptions(Long patientId){
        if(prescriptionRepository.countPrescriptionByCurrentMonthAndYear(patientId) >= 3) {
            throw new IllegalArgumentException("No se puede prescribir más de 3 medicamentos por mes.");
        }
    }

    private Boolean getNumberPrescriptions(Long patientId){
        return prescriptionRepository.countPrescriptionByCurrentMonthAndYear(patientId) >= 3;
    }

    private void verifyMinimumAgeDrug(Patient patient,Medicament medicament){
        if(patient.getAge() < medicament.getMinimumAgeConsumption() ){
            throw new IllegalArgumentException("El paciente no tiene la edad mínima para tomar el medicamento.");
        }
        int maxAge = medicament.getMaxAgeConsumption();
        if( maxAge > 0 && patient.getAge() > maxAge){
            throw new IllegalArgumentException(
                    String.format("El paciente supera la edad máxima permitida para el medicamento %s  que es de %d años",
                            medicament.getName(),maxAge));
        }
    }

    private void checkPrescriptionByCurrentMonthAndMedicamentId(Patient patient,Medicament medicament){
        int countPrescriptionByMedicament = prescriptionRepository
                .countPrescriptionByCurrentMonthAndMedicamentId(patient.getId(),medicament.getId());
        if (countPrescriptionByMedicament > 0) {
            LocalDate date = ZonedDateTime.now().toLocalDate();
            throw new IllegalArgumentException(String.format("No se puede prescribir más de un medicamento con id %d en el mes %s del %d.",
                    medicament.getId(),date.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")),date.getYear()));
        }
    }

    @Override
    public Page<PrescriptionResponseDTO> getPagePrescriptionByPatient(Long patientId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Prescription> prescriptions = prescriptionRepository.findByUser(patientId,pageable);
        Page<PrescriptionResponseDTO> dtoPage = prescriptions.map(new Function<Prescription, PrescriptionResponseDTO>() {
            @Override
            public PrescriptionResponseDTO apply(Prescription prescription) {
                return prescriptionDtoFactory.convertEntityToDTO(prescription);
            }
        });
        return dtoPage;
    }
}
