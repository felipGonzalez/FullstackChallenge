package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.configuration;

import com.saludtools.fullstackchallengehexagonalarchitecture.FullStackChallengeHexagonalArchitectureApplication;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.MedicamentRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PatientRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PrescriptionRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = FullStackChallengeHexagonalArchitectureApplication.class)
public class BeanConfiguration {

    @Bean
    PatientService patientService(final PatientRepository patientRepository) {
        return new PatientServiceImp(patientRepository);
    }

    @Bean
    MedicamentService medicamentService(final MedicamentRepository medicamentRepository) {
        return new MedicamentServiceImp(medicamentRepository);
    }

    @Bean
    PrescriptionService prescriptionService(final PrescriptionRepository prescriptionRepository,
                                            final PatientRepository patientRepository,
                                            final MedicamentRepository medicamentRepository){
        return new PrescriptionServiceImp(prescriptionRepository, patientRepository, medicamentRepository);
    }

}
