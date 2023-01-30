package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Medicament;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Prescription;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.MedicamentRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PatientRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PrescriptionRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    private PrescriptionServiceImp prescriptionServiceImp;

    private PrescriptionRepository prescriptionRepository;

    private PatientRepository patientRepository;

    private MedicamentRepository medicamentRepository;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        prescriptionRepository = mock(PrescriptionRepository.class);
        patientRepository =  mock(PatientRepository.class);
        medicamentRepository =  mock(MedicamentRepository.class);

        prescriptionServiceImp = new PrescriptionServiceImp(prescriptionRepository,patientRepository,medicamentRepository);
    }

    @Test
    public void createPrescription_whenPatientAndMedicamentExist_shouldSavePrescription() {
        // Arrange

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
        prescription.setId(1L);

        when(patientRepository.findByIdAndEnabled(patient.getId())).thenReturn(Optional.of(patient));
        when(medicamentRepository.findById(medicament.getId())).thenReturn(Optional.of(medicament));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(prescription);

        // Act
        Prescription prescriptionResult = prescriptionServiceImp.createPrescription(patient.getId(),medicament.getId());

        // Assert
        Assertions.assertEquals(datePrescription, prescriptionResult.getDatePrescription());
        Assertions.assertEquals(patient, prescriptionResult.getPatient());
        Assertions.assertEquals(medicament, prescriptionResult.getMedicament());
        verify(patientRepository, times(1)).findByIdAndEnabled(patient.getId());
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
        Page<Prescription> result = prescriptionServiceImp.getPagePrescriptionByPatient(patientId, page, size);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
    }

}
