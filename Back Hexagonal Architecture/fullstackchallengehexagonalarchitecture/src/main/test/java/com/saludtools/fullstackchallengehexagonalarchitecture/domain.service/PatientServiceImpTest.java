package com.saludtools.fullstackchallengehexagonalarchitecture.domain.service;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Gender;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Patient;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PatientRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.validator.PatientValidation;
import org.mockito.*;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
public class PatientServiceImpTest {

    private PatientServiceImp patientServiceImp;

    private PatientRepository patientRepository;


    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patientRepository =  mock(PatientRepository.class);
        patientServiceImp = new PatientServiceImp(patientRepository);

    }


    @Test
    public void createPatientTest() {

        Patient patient = new Patient();
        patient.setName("John");
        patient.setLastName("Doe");
        patient.setGender(Gender.MALE);
        patient.setBirthDate(LocalDate.of(2000, 1, 1));

        try (MockedStatic<PatientValidation> patientValidationMockedStatic = Mockito.mockStatic(PatientValidation.class)) {
            //Simular la respuesta del metódo estático del objeto Mock

            patientValidationMockedStatic.when(() -> PatientValidation.validateDataForCreate(patient))
                    .thenAnswer((Answer<Void>) invocation -> null);
            patientValidationMockedStatic.when(() -> PatientValidation.validateName(anyString()))
                    .thenAnswer((Answer<Void>) invocation -> null);
            patientValidationMockedStatic.when(() -> PatientValidation.validateLastName(anyString()))
                    .thenAnswer((Answer<Void>) invocation -> null);
            patientValidationMockedStatic.when(() -> PatientValidation.validateGender(Gender.MALE))
                    .thenAnswer((Answer<Void>) invocation -> null);
            patientValidationMockedStatic.when(() -> PatientValidation.validateBirthDate(LocalDate.now()))
                    .thenAnswer((Answer<Void>) invocation -> null);

        }
        when(patientRepository.save(patient)).thenReturn(patient);
        Patient result = patientServiceImp.createPatient(patient);
        Assertions.assertEquals(patient, result);
    }

    @Test
    public void deletePatientTest_whenIdPassed_shouldDeletePatientById() {
        Long id = 1L;
        Patient patient = new Patient();
        patient.setId(id);
        when(patientRepository.findByIdAndEnabled(id)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        patientServiceImp.deletePatient(id);
        verify(patientRepository, times(1)).findByIdAndEnabled(id);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void getAllPatientTest_whenPageAndSizePassed_shouldReturnPageOfPatientDTOWithIsPrescriptionAllowed() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        List<Patient> patientList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Patient patient = new Patient();
            patient.setId((long) i);
            patient.setIsPrescriptionAllowed(true);
            patientList.add(patient);
        }

        Page<Patient> patientPage = new PageImpl<>(patientList);
        when(patientRepository.findAllPatients(pageable)).thenReturn(patientPage);

        Page<Patient> patientsResponse = patientServiceImp.getAllPatient(page, size,"","");

        for (Patient patient : patientsResponse) {
            Assertions.assertEquals(true,patient.getIsPrescriptionAllowed());
        }
    }

}
