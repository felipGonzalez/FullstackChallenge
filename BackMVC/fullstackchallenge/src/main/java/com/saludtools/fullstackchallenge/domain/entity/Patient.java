package com.saludtools.fullstackchallenge.domain.entity;

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
public class Patient implements Serializable {

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
	private Gender gender;

	@Column(nullable = false,columnDefinition = "boolean default true")
	private Boolean enabled;

	private static final long serialVersionUID = 1L;

	public Patient(Long id, String name, String lastName, LocalDate birthDate, Gender gender) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	public int getAge() {
		LocalDate now = LocalDate.now();
		Period period = Period.between(this.birthDate, now);
		return period.getYears();
	}

}
