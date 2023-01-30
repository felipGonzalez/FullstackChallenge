package com.saludtools.fullstackchallengehexagonalarchitecture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class PrescriptionDTO {

    private Long id;

    private Long patientId;

    private Long medicamentId;
}
