package com.saludtools.fullstackchallenge.Service;

import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionDTO;
import com.saludtools.fullstackchallenge.Domain.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallenge.Domain.entity.Gender;
import com.saludtools.fullstackchallenge.Domain.entity.Medicament;
import com.saludtools.fullstackchallenge.Domain.entity.Patient;
import com.saludtools.fullstackchallenge.Domain.entity.Prescription;
import com.saludtools.fullstackchallenge.DtoFactory.PrescriptionDtoFactory;
import com.saludtools.fullstackchallenge.Repository.MedicamentRepository;
import com.saludtools.fullstackchallenge.Repository.PatientRepository;
import com.saludtools.fullstackchallenge.Repository.PrescriptionRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PrescriptionServiceImpTest {

    @InjectMocks
    private PrescriptionServiceImp prescriptionServiceImp;

    @Mock
    private PrescriptionRepository prescriptionRepository;
    @Mock
    private PrescriptionDtoFactory prescriptionDtoFactory;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicamentRepository medicamentRepository;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPrescription_whenPatientAndMedicamentExist_shouldSavePrescription() {
        // Arrange
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        prescriptionDTO.setPatientId(1L);
        prescriptionDTO.setMedicamentId(1L);
        prescriptionDTO.setDatePrescription(LocalDate.now());

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setBirthDate(LocalDate.now().minusYears(25));
        patient.setGender(Gender.FEMALE);

        Medicament medicament = new Medicament();
        medicament.setId(1L);
        medicament.setMinimumAgeConsumption((short)18);
        medicament.setMaxAgeConsumption((short)65);
        medicament.setExclusiveUse(Gender.FEMALE);

        LocalDate datePrescription = LocalDate.now();

        Prescription prescription = new Prescription();
        prescription.setMedicament(medicament);
        prescription.setPatient(patient);
        prescription.setDatePrescription(datePrescription);

        when(prescriptionDtoFactory.convertDtoToEntity(prescriptionDTO)).thenReturn(prescription);
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(medicamentRepository.findById(medicament.getId())).thenReturn(Optional.of(medicament));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(new Prescription(1L, LocalDate.now(), patient, medicament));

        // Act
        Prescription prescriptionResult = prescriptionServiceImp.createPrescription(prescriptionDTO);

        // Assert
        Assertions.assertNotNull(prescriptionResult);
        Assertions.assertEquals(datePrescription, prescriptionResult.getDatePrescription());
        Assertions.assertEquals(patient, prescriptionResult.getPatient());
        Assertions.assertEquals(medicament, prescriptionResult.getMedicament());
        verify(patientRepository, times(1)).findById(patient.getId());
        verify(medicamentRepository, times(1)).findById(medicament.getId());
        verify(prescriptionRepository, times(1)).save(any(Prescription.class));
    }

    @Test
    public void getPagePrescriptionByPatient_whenValidPatientId_shouldReturnPagePrescriptionResponseDto() {
        // Arrange
        Long patientId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(new Prescription());
        Page<Prescription> prescriptionPage = new PageImpl<>(prescriptions);
        when(prescriptionRepository.findByUser(patientId, pageable)).thenReturn(prescriptionPage);

        // Act
        Page<PrescriptionResponseDTO> result = prescriptionServiceImp.getPagePrescriptionByPatient(patientId, page, size);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
    }

}
