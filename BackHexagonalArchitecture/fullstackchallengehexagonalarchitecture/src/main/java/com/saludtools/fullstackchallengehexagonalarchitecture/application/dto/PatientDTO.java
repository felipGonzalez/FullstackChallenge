package com.saludtools.fullstackchallengehexagonalarchitecture.application.dto;

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

    private String gender;

    private Boolean isPrescriptionAllowed;
}
