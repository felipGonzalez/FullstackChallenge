package com.saludtools.fullstackchallengehexagonalarchitecture.Domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Medicaments")
public class Medicament implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column( name = "name")
	private String name;

	@Column( name = "minimum_age_consumption")
	private short minimumAgeConsumption;

	@Column( name = "max_age_consumption")
	private short maxAgeConsumption;

	@Enumerated(EnumType.STRING)
	@Column( name = "exclusive_use")
	private Gender exclusiveUse;

	private static final long serialVersionUID = 1L;

}
