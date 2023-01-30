package com.saludtools.fullstackchallengehexagonalarchitecture.Builder;

import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.dto.MedicamentDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.Domain.entity.Gender;
import org.springframework.stereotype.Component;

@Component
public class MedicamentDTOBuilder {

    private Long id;
    private String name;
    private short minimumAgeConsumption;
    private short maxAgeConsumption;
    private Gender exclusiveUse;

    public MedicamentDTOBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public MedicamentDTOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MedicamentDTOBuilder withMinimumAgeConsumption(short minimumAgeConsumption) {
        this.minimumAgeConsumption = minimumAgeConsumption;
        return this;
    }

    public MedicamentDTOBuilder withMaxAgeConsumption(short maxAgeConsumption) {
        this.maxAgeConsumption = maxAgeConsumption;
        return this;
    }

    public MedicamentDTOBuilder withExclusiveUse(Gender exclusiveUse) {
        this.exclusiveUse = exclusiveUse;
        return this;
    }

    public MedicamentDTO build() {
        return new MedicamentDTO(id, name, minimumAgeConsumption, maxAgeConsumption, exclusiveUse);
    }
}
