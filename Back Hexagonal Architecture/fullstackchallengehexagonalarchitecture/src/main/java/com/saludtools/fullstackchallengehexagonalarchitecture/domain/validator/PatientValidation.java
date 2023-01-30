package com.saludtools.fullstackchallengehexagonalarchitecture.domain.validator;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.DomainException;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;

import java.time.LocalDate;

public class PatientValidation {

    public static final int MAX_NAME_LENGTH = 100;

    public static void validateDataForCreate(Patient patient){
        validateName(patient.getName());
        validateLastName(patient.getLastName());
        validateGender(patient.getGender());
        validateBirthDate(patient.getBirthDate());
    }

    public static void validateDataForUpdate(Patient patient){
        validateId(patient.getId());
        validateName(patient.getName());
        validateLastName(patient.getLastName());
        validateGender(patient.getGender());
        validateBirthDate(patient.getBirthDate());
    }

    public static void validateId(Long id) {
        if (id == null) {
            throw new DomainException("La ID no puede ser nula.");
        }
    }

    public static void validateName(String name) {
        if (name == null) {
            throw new DomainException("El nombre no puede ser nulo");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new DomainException("El nombre debe tener menos de 100 caracteres");
        }
    }

    public static void validateLastName(String lastName) {
        if (lastName == null) {
            throw new DomainException("El apellido no puede ser nulo");
        }

        if (lastName.length() > MAX_NAME_LENGTH) {
            throw new DomainException("El apellido debe tener menos de 100 caracteres.");
        }
    }

    public static void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new DomainException("Fecha de nacimiento no puede ser nulo");
        }
    }

    public static void validateGender(Gender gender) {
        if (gender == null) {
            throw new DomainException("Gender cannot be null");
        }

    }
}
