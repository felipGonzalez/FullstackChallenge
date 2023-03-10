package com.saludtools.fullstackchallenge.service;

import com.saludtools.fullstackchallenge.domain.dto.MedicamentDTO;
import com.saludtools.fullstackchallenge.dto_factory.MedicamentDTOFactory;
import com.saludtools.fullstackchallenge.repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MedicamentServiceImp implements MedicamentService{

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private MedicamentDTOFactory medicamentDTOFactory;

    @Override
    public List<MedicamentDTO> getAllMedicament() {
        return medicamentDTOFactory.convertEntityToDTO(medicamentRepository.findAll());
    }

    @Override
    public List<MedicamentDTO> searchByName(String text) {
        if(text.length() <= 2){
            return medicamentDTOFactory.convertEntityToDTO(medicamentRepository.
                    findTop20ByNameContains(text));
        }
        text = "%"+text.trim().replaceAll("\\s", "%")+"%";
        return medicamentDTOFactory.convertEntityToDTO(medicamentRepository.findByTerm(text));
    }
}
