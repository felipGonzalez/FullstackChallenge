package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.medicament_repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;
import com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.GenderEnumEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Medicaments")
public class MedicamentEntity implements Serializable {

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
	private GenderEnumEntity exclusiveUse;

	private static final long serialVersionUID = 1L;

	public MedicamentEntity(Medicament medicament){
		this.id = medicament.getId();
		this.name = medicament.getName();
		this.minimumAgeConsumption = medicament.getMinimumAgeConsumption();
		this.maxAgeConsumption = medicament.getMaxAgeConsumption();
		if(medicament.getExclusiveUse() != null){
			this.exclusiveUse = GenderEnumEntity.findByValue(medicament.getExclusiveUse().getValue());
		}
	}

	public Medicament toMedicament(){
		return new Medicament(this.id,this.name,
				this.minimumAgeConsumption,
				this.maxAgeConsumption,
				GenderEnumEntity.toGender(this.exclusiveUse));
	}

}
