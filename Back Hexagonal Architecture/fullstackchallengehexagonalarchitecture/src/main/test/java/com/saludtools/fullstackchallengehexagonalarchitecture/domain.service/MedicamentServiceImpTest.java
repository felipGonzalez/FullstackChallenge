package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.MedicamentRepository;
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

    private MedicamentServiceImp medicamentServiceImp;

    @Mock
    private MedicamentRepository medicamentRepository;

    @BeforeEach()
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        medicamentRepository = mock(MedicamentRepository.class);
        medicamentServiceImp = new MedicamentServiceImp(medicamentRepository);
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

        List<Medicament> result = medicamentServiceImp.getAllMedicament();

        Assertions.assertEquals(medicamentList, result);
    }
}
