package com.saludtools.fullstackchallenge.DtoFactory;

import com.saludtools.fullstackchallenge.Builder.PrescriptionResponseDTOBuilder;
import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionDTO;
import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallenge.Domain.entity.Medicament;
import com.saludtools.fullstackchallenge.Domain.entity.Patient;
import com.saludtools.fullstackchallenge.Domain.entity.Prescription;
import com.saludtools.fullstackchallenge.Repository.MedicamentRepository;
import com.saludtools.fullstackchallenge.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
@Component
public class PrescriptionDtoFactory {

    @Autowired
    private PatientDtoFactory patientDtoFactory;

    @Autowired
    private MedicamentDTOFactory medicamentDTOFactory;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;

    public PrescriptionResponseDTO convertEntityToDTO(Prescription prescription){
        if(isNull(prescription)) return new PrescriptionResponseDTO();
        return new PrescriptionResponseDTOBuilder()
                .withId(prescription.getId())
                .withDatePrescription(prescription.getDatePrescription())
                .withPatient(patientDtoFactory.convertEntityToDTO(prescription.getPatient()))
                .withMedicament(medicamentDTOFactory.convertEntityToDTO(prescription.getMedicament()))
                .build();
    }

    public Prescription convertDtoToEntity(PrescriptionDTO prescriptionDTO){
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionDTO.getId());
        prescription.setDatePrescription(getDatePrescription(prescriptionDTO.getDatePrescription()));
        prescription.setPatient(getPatient(prescriptionDTO.getPatientId()));
        prescription.setMedicament(getMedicament(prescriptionDTO.getMedicamentId()));
        return prescription;
    }

    private LocalDate getDatePrescription(LocalDate datePrescription) {
        if (isNull(datePrescription)) {
            return ZonedDateTime.now().toLocalDate();
        }
        return datePrescription;
    }

    private Patient getPatient(Long patientId) {
        if(isNull(patientId) ){
            throw new IllegalArgumentException("Id paciente no puede ser nulo");
        }

        Optional<Patient> patientOptional = patientRepository.findById(patientId);

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

    public List<PrescriptionResponseDTO> convertEntityToDTO(List<Prescription> prescriptions) {
        List<PrescriptionResponseDTO> response = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            response.add(convertEntityToDTO(prescription));
        }
        return response;
    }
}
