package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Prescription;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.MedicamentRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PatientRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PrescriptionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Optional;

import static java.util.Objects.isNull;

public class PrescriptionServiceImp implements PrescriptionService{

    private final PrescriptionRepository prescriptionRepository;

    private final PatientRepository patientRepository;

    private  final MedicamentRepository medicamentRepository;

    public PrescriptionServiceImp(PrescriptionRepository prescriptionRepository, PatientRepository patientRepository, MedicamentRepository medicamentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
        this.medicamentRepository = medicamentRepository;
    }

    @Override
    public Prescription createPrescription(Long patientId, Long medicamentId) {
        Patient patient = getPatient(patientId);
        Medicament medicament = getMedicament(medicamentId);
        validatePrescription(patient,medicament);
        Prescription prescription = new Prescription();
        prescription.setDatePrescription(LocalDate.now());
        prescription.setPatient(patient);
        prescription.setMedicament(medicament);
        return prescriptionRepository.save(prescription);
    }

    private Patient getPatient(Long patientId) {
        if(isNull(patientId) ){
            throw new IllegalArgumentException("Id paciente no puede ser nulo");
        }
        Optional<Patient> patientOptional = patientRepository.findByIdAndEnabled(patientId);
        if(Boolean.FALSE.equals(patientOptional.isPresent())){
            throw new IllegalArgumentException(String.format("Paciente con id %d no encontrado",patientId));
        }
        return patientOptional.get();
    }

    private Medicament getMedicament(Long medicamentId) {
        if(isNull(medicamentId) ){
            throw new IllegalArgumentException("Id medicamento no puede ser nulo");
        }

        Optional<Medicament> medicamentOptional = medicamentRepository.findById(medicamentId);

        if(Boolean.FALSE.equals(medicamentOptional.isPresent())){
            throw new IllegalArgumentException(String.format("Medicamento con id %d no encontrado",medicamentId));
        }
        return medicamentOptional.get();
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
    public Page<Prescription> getPagePrescriptionByPatient(Long patientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Prescription> prescriptions = prescriptionRepository.findByUser(patientId,pageable);
        return prescriptions;
    }
}
