package com.saludtools.fullstackchallengehexagonalarchitecture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponseDTO {

    private Long id;

    private LocalDate datePrescription;

    private PatientDTO patient;

    private MedicamentDTO medicament;

    public Long getId() {
        return id;
    }

    public LocalDate getDatePrescription() {
        return datePrescription;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public MedicamentDTO getMedicament() {
        return medicament;
    }
}
