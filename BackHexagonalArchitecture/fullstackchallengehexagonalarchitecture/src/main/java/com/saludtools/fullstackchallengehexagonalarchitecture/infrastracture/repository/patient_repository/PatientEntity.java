package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.patient_repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.GenderEnumEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Patients")
@AllArgsConstructor
public class PatientEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 100)
	private String lastName;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GenderEnumEntity gender;

	@Column(nullable = false,columnDefinition = "boolean default true")
	private Boolean enabled;

	private static final long serialVersionUID = 1L;

	public PatientEntity(Long id, String name, String lastName, LocalDate birthDate, GenderEnumEntity gender) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	public PatientEntity(Patient patient) {
		this.id = patient.getId();
		this.name = patient.getName();
		this.lastName = patient.getLastName();
		this.birthDate = patient.getBirthDate();
		this.gender = GenderEnumEntity.valueOf(patient.getGender().toString());
		this.enabled = patient.getEnabled();
	}

	public Patient toPatient(){
		return new Patient(this.id,this.name,this.lastName,this.birthDate,
				GenderEnumEntity.toGender(this.gender), this.enabled);
	}

	public int getAge() {
		LocalDate now = LocalDate.now();
		Period period = Period.between(this.birthDate, now);
		return period.getYears();
	}

}
