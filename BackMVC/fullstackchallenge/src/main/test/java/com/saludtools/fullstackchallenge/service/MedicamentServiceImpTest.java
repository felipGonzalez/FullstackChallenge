package com.saludtools.fullstackchallenge.service;

import com.saludtools.fullstackchallenge.domain.dto.MedicamentDTO;
import com.saludtools.fullstackchallenge.domain.entity.Medicament;
import com.saludtools.fullstackchallenge.dto_factory.MedicamentDTOFactory;
import com.saludtools.fullstackchallenge.repository.MedicamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MedicamentServiceImpTest {

    @InjectMocks
    private MedicamentServiceImp medicamentServiceImp;

    @Mock
    private MedicamentRepository medicamentRepository;

    @Mock
    private MedicamentDTOFactory medicamentDTOFactory;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllMedicament_whenFindAllIsCalled_shouldReturnAllMedicamentsDTO() {
        //Arrange
        Medicament medicament1 = new Medicament();
        medicament1.setId(1L);
        medicament1.setName("Ibuprofeno");
        Medicament medicament2 = new Medicament();
        medicament2.setId(2L);
        medicament2.setName("Aspirina");
        List<Medicament> medicamentList = Arrays.asList(medicament1, medicament2);

        when(medicamentRepository.findAll()).thenReturn(medicamentList);

        MedicamentDTO medicamentDTO1 = new MedicamentDTO();
        medicamentDTO1.setId(1L);
        medicamentDTO1.setName("Ibuprofeno");
        MedicamentDTO medicamentDTO2 = new MedicamentDTO();
        medicamentDTO2.setId(2L);
        medicamentDTO2.setName("Aspirina");

        List<MedicamentDTO> medicamentDTOList = Arrays.asList(medicamentDTO1, medicamentDTO2);

        when(medicamentDTOFactory.convertEntityToDTO(medicamentList)).thenReturn(medicamentDTOList);

        List<MedicamentDTO> result = medicamentServiceImp.getAllMedicament();

        Assertions.assertEquals(medicamentDTOList, result);
    }
}
