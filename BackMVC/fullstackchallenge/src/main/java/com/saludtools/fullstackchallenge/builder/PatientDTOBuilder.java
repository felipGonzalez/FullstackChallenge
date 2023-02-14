package com.saludtools.fullstackchallenge.builder;

import com.saludtools.fullstackchallenge.domain.dto.PatientDTO;
import com.saludtools.fullstackchallenge.domain.entity.Gender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.util.Objects.nonNull;

@Component
public class PatientDTOBuilder {

    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;

    public PatientDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PatientDTOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PatientDTOBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PatientDTOBuilder withBirthDate(LocalDate birthDate) {
        if(nonNull(birthDate)){
            this.birthDate = birthDate;
        }
        return this;
    }

    public PatientDTOBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PatientDTO build() {
        return new PatientDTO(id, name, lastName, birthDate, gender);
    }
}
