package com.saludtools.fullstackchallenge.DtoFactory;

import com.saludtools.fullstackchallenge.Builder.MedicamentDTOBuilder;
import com.saludtools.fullstackchallenge.Domain.dto.MedicamentDTO;
import com.saludtools.fullstackchallenge.Domain.entity.Medicament;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class MedicamentDTOFactory {

    public MedicamentDTO convertEntityToDTO(Medicament medicament){
        if(isNull(medicament)) return new MedicamentDTO();
        return new MedicamentDTOBuilder()
                .withId(medicament.getId())
                .withName(medicament.getName())
                .withMinimumAgeConsumption(medicament.getMinimumAgeConsumption())
                .withMaxAgeConsumption(medicament.getMaxAgeConsumption())
                .withExclusiveUse(medicament.getExclusiveUse())
                .build();
    }

    public List<MedicamentDTO> convertEntityToDTO(List<Medicament> medicaments) {
        List<MedicamentDTO> response = new ArrayList<>();
        for (Medicament medicament : medicaments) {
            response.add(convertEntityToDTO(medicament));
        }
        return response;
    }

}
