package com.saludtools.fullstackchallenge.domain.dto;

import com.saludtools.fullstackchallenge.domain.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Long id;

    private String name;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;

    private Boolean isPrescriptionAllowed;

    public PatientDTO(Long id, String name, String lastName, LocalDate birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
