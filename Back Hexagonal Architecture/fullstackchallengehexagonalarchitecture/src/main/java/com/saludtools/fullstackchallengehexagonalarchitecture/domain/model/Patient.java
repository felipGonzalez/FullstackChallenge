package com.saludtools.fullstackchallengehexagonalarchitecture.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	private String name;

	private String lastName;

	private LocalDate birthDate;

	private Gender gender;

	private Boolean isPrescriptionAllowed;

	private Boolean enabled;

	public Patient(Long id, String name, String lastName, LocalDate birthDate, Gender gender,Boolean enabled) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.enabled = enabled;
	}

	public Patient(Long id, String name, String lastName, LocalDate birthDate, Gender gender) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	public void update(Long id, Boolean enabled){
		this.id = id;
		this.enabled = enabled;
	}


	public int getAge() {
		LocalDate now = LocalDate.now();
		Period period = Period.between(this.birthDate, now);
		return period.getYears();
	}

}
