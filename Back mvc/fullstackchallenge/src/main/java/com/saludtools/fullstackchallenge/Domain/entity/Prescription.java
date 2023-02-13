package com.saludtools.fullstackchallenge.Domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Prescriptions")
public class Prescription implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDate datePrescription;

	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "medicament_id")
	private Medicament medicament;

	private static final long serialVersionUID = 1L;
}
