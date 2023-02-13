package com.saludtools.fullstackchallenge.Domain.dto;

import com.saludtools.fullstackchallenge.Domain.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentDTO {

    private Long id;

    private String name;

    private short minimumAgeConsumption;

    private short maxAgeConsumption;

    private Gender exclusiveUse;

}
