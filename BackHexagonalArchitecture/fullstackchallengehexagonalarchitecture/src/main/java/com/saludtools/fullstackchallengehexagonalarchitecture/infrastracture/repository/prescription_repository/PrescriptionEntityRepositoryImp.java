package com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.prescription_repository;

import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Prescription;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.repository.PrescriptionRepository;
import com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.medicament_repository.MedicamentEntity;
import com.saludtools.fullstackchallengehexagonalarchitecture.infrastracture.repository.patient_repository.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PrescriptionEntityRepositoryImp  implements PrescriptionRepository {

    private final SpringDataPrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionEntityRepositoryImp(SpringDataPrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public Integer countPrescriptionByCurrentMonthAndYear(Long userId) {
        return prescriptionRepository.countPrescriptionByCurrentMonthAndYear(userId);
    }

    @Override
    public Integer countPrescriptionByCurrentMonthAndMedicamentId(Long userId, Long medicamentId) {
        return prescriptionRepository.countPrescriptionByCurrentMonthAndMedicamentId(userId,medicamentId);
    }

    @Override
    public Page<Prescription> findByUser(Long patientId, Pageable pageable) {
        Page<PrescriptionEntity> prescriptionEntities = prescriptionRepository.findByUser(patientId,pageable);
        return prescriptionEntities.map(new Function<PrescriptionEntity, Prescription>() {
            @Override
            public Prescription apply(PrescriptionEntity prescriptionEntity) {
                return prescriptionEntity.toPrescription();
            }
        });
    }

    @Override
    public Prescription save(Prescription prescription) {
        PatientEntity patientEntity = new PatientEntity(prescription.getPatient());
        MedicamentEntity medicamentEntity = new MedicamentEntity(prescription.getMedicament());
        PrescriptionEntity prescriptionEntity = prescriptionRepository.save(new PrescriptionEntity(prescription.getId(),
                prescription.getDatePrescription(),patientEntity,medicamentEntity));
        return prescriptionEntity.toPrescription();
    }
}
