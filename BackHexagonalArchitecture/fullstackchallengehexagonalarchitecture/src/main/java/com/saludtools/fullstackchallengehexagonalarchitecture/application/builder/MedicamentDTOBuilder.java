package com.saludtools.fullstackchallengehexagonalarchitecture.application.builder;

import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto.MedicamentDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;
import org.springframework.stereotype.Component;

@Component
public class MedicamentDTOBuilder {

    private Long id;
    private String name;
    private short minimumAgeConsumption;
    private short maxAgeConsumption;
    private String exclusiveUse;

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
        if(exclusiveUse == null) {
            return this;
        }
        this.exclusiveUse = exclusiveUse.getValue();
        return this;
    }

    public MedicamentDTO build() {
        return new MedicamentDTO(id, name, minimumAgeConsumption, maxAgeConsumption, exclusiveUse);
    }
}
