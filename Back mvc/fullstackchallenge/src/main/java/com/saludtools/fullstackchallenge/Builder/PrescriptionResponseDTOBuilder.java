package com.saludtools.fullstackchallenge.Builder;

import com.saludtools.fullstackchallenge.Domain.dto.MedicamentDTO;
import com.saludtools.fullstackchallenge.Domain.dto.PatientDTO;
import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionResponseDTO;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static java.util.Objects.nonNull;

public class PrescriptionResponseDTOBuilder {

    private Long id;
    private LocalDate datePrescription;
    private PatientDTO patient;
    private MedicamentDTO medicament;

    public PrescriptionResponseDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PrescriptionResponseDTOBuilder withDatePrescription(LocalDate datePrescription) {
        if(nonNull(datePrescription)){
            this.datePrescription = ZonedDateTime.now().toLocalDate();
        }
        return this;
    }

    public PrescriptionResponseDTOBuilder withPatient(PatientDTO patient) {
        this.patient = patient;
        return this;
    }

    public PrescriptionResponseDTOBuilder withMedicament(MedicamentDTO medicament) {
        this.medicament = medicament;
        return this;
    }

    public PrescriptionResponseDTO build() {
        return new PrescriptionResponseDTO(id, datePrescription, patient, medicament);
    }
}
