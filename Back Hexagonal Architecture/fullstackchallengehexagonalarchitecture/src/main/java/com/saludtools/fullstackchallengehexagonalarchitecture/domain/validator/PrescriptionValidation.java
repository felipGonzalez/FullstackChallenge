package com.saludtools.fullstackchallengehexagonalarchitecture.domain.validator;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.DomainException;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;

public class PrescriptionValidation {

    public void withPatient(Patient  patient) {
        if (patient == null || patient.getId() == null ) {
            throw new DomainException("El Paciente no pude ser nulo");
        }
    }

    public void withMedicament(Medicament medicament) {
        if (medicament == null || medicament.getId() == null) {
            throw new DomainException("El medicamento no pude ser nulo.");
        }
    }
}
