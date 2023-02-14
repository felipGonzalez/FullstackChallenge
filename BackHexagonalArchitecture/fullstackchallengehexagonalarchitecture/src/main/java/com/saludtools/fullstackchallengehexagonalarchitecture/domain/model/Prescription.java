package com.saludtools.fullstackchallengehexagonalarchitecture.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription implements Serializable {

	private Long id;

	private LocalDate datePrescription;

	private Patient patient;

	private Medicament medicament;

	private static final long serialVersionUID = 1L;

	public Prescription(Patient patient, Medicament medicament) {
		this.patient = patient;
		this.medicament = medicament;
		this.datePrescription = LocalDate.now();
	}
}
