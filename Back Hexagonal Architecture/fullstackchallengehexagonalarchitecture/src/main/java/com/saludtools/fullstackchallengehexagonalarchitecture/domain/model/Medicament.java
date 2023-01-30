package com.saludtools.fullstackchallengehexagonalarchitecture.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicament implements Serializable {

	private Long id;

	private String name;

	private short minimumAgeConsumption;

	private short maxAgeConsumption;

	private Gender exclusiveUse;

	private static final long serialVersionUID = 1L;

}
