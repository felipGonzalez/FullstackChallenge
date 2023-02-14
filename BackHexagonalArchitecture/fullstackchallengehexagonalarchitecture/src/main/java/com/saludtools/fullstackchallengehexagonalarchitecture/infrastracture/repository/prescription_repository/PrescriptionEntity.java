package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.prescription_repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Prescription;
import com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.medicament_repository.MedicamentEntity;
import com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.patient_repository.PatientEntity;
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
public class PrescriptionEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDate datePrescription;

	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private PatientEntity patient;

	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "medicament_id")
	private MedicamentEntity medicament;

	private static final long serialVersionUID = 1L;

	public Prescription toPrescription(){
		return new Prescription(
				this.id,
				this.datePrescription,
				this.patient.toPatient(),
				this.medicament.toMedicament());
	}

}
