package com.saludtools.fullstackchallenge.Service;

import com.saludtools.fullstackchallenge.Domain.dto.PatientDTO;
import com.saludtools.fullstackchallenge.Domain.entity.Gender;
import com.saludtools.fullstackchallenge.Domain.entity.Patient;
import com.saludtools.fullstackchallenge.DtoFactory.PatientDtoFactory;
import com.saludtools.fullstackchallenge.Repository.PatientRepository;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
public class PatientServiceImpTest {

    @InjectMocks
    private PatientServiceImp patientServiceImp;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientDtoFactory patientDtoFactory;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void createPatientTest() {
        //Arrange
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setName("John");
        patientDTO.setLastName("Doe");
        patientDTO.setGender(Gender.MALE);
        patientDTO.setBirthDate(LocalDate.of(2000, 1, 1));

        Patient patient = new Patient();
        patient.setName("John");
        patient.setLastName("Doe");
        patient.setGender(Gender.MALE);
        patient.setBirthDate(LocalDate.of(2000, 1, 1));

        when(patientDtoFactory.convertDtoToEntity(patientDTO)).thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientDtoFactory.convertEntityToDTO(patient)).thenReturn(patientDTO);

        PatientDTO result = patientServiceImp.createPatient(patientDTO);

        Assertions.assertEquals(patientDTO, result);
    }

    @Test
    public void deletePatientTest_whenIdPassed_shouldDeletePatientById() {
        Long id = 1L;
        Patient patient = new Patient();
        patient.setId(id);
        when(patientRepository.findByIdAndEnabled(id)).thenReturn(Optional.of(patient));
        patientServiceImp.deletePatient(id);
        verify(patientRepository, times(1)).findByIdAndEnabled(id);
        verify(patientRepository, times(1)).findByIdAndEnabled(id);
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
            patientList.add(patient);
        }

        List<PatientDTO> patientDTOList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Patient patient = new Patient();
            patient.setId((long) i);
            patientList.add(patient);
        }


        Page<PatientDTO> patientPage = new PageImpl<>(patientDTOList);
        when(patientRepository.findAllPatients(pageable)).thenReturn(patientPage);

        for (int i = 0; i < size; i++) {
            when(patientRepository.countPrescriptionByCurrentMonthAndYear((long) i)).thenReturn(2);
        }

        Page<PatientDTO> patientDTOPage = patientServiceImp.getAllPatient(page, size,"","");

        for (PatientDTO patientDTO : patientDTOPage) {
            Assertions.assertEquals(patientDTO.getIsPrescriptionAllowed(), true);
        }
    }

}
