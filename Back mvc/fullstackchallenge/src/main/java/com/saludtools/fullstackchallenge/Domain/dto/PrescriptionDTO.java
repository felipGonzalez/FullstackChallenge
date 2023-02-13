package com.saludtools.fullstackchallenge.Domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class PrescriptionDTO {

    private Long id;

    private LocalDate datePrescription;

    private Long patientId;

    private Long medicamentId;
}
