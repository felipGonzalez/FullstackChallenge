package com.saludtools.fullstackchallenge.builder;

import com.saludtools.fullstackchallenge.domain.entity.Gender;
import com.saludtools.fullstackchallenge.domain.entity.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PatientBuilder {

    public static final int MAX_NAME_LENGTH = 100;

    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;

    public PatientBuilder withId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("La ID no puede ser nula.");
        }
        this.id = id;
        return this;
    }

    public PatientBuilder withName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("El nombre debe tener menos de 100 caracteres");
        }
        this.name = name;
        return this;
    }

    public PatientBuilder withLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("El apellido no puede ser nulo");
        }

        if (lastName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("El apellido debe tener menos de 100 caracteres.");
        }
        this.lastName = lastName;
        return this;
    }

    public PatientBuilder withBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Fecha de nacimiento no puede ser nulo");
        }
        this.birthDate =  birthDate;
        return this;
    }

    public PatientBuilder withGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        this.gender = gender;
        return this;
    }

    public Patient build() {
        return new Patient(id, name, lastName, birthDate, gender);
    }
}
