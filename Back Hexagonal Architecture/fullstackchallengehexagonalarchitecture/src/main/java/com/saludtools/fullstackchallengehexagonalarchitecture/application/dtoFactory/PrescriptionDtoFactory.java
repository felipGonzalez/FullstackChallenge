package com.saludtools.fullstackchallengehexagonalarchitecture.application.dtoFactory;

import com.saludtools.fullstackchallengehexagonalarchitecture.application.builder.PrescriptionResponseDTOBuilder;
import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto.PrescriptionDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.application.dto.PrescriptionResponseDTO;
import com.saludtools.fullstackchallengehexagonalarchitecture.domain.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.isNull;
@Component
public class PrescriptionDtoFactory {

    @Autowired
    private PatientDtoFactory patientDtoFactory;

    @Autowired
    private MedicamentDTOFactory medicamentDTOFactory;

    public PrescriptionResponseDTO convertEntityToDTO(Prescription prescription){
        if(isNull(prescription)) return new PrescriptionResponseDTO();
        return new PrescriptionResponseDTOBuilder()
                .withId(prescription.getId())
                .withDatePrescription(prescription.getDatePrescription())
                .withPatient(patientDtoFactory.convertEntityToDTO(prescription.getPatient()))
                .withMedicament(medicamentDTOFactory.convertEntityToDTO(prescription.getMedicament()))
                .build();
    }

    public Prescription convertDtoToEntity(PrescriptionDTO prescriptionDTO){
        Prescription prescription = new Prescription();
        prescription.setId(prescriptionDTO.getId());
        return prescription;
    }

    private LocalDate getDatePrescription(LocalDate datePrescription) {
        if (isNull(datePrescription)) {
            return ZonedDateTime.now().toLocalDate();
        }
        return datePrescription;
    }

    public List<PrescriptionResponseDTO> convertEntityToDTO(List<Prescription> prescriptions) {
        List<PrescriptionResponseDTO> response = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            response.add(convertEntityToDTO(prescription));
        }
        return response;
    }

    public Page<PrescriptionResponseDTO> convertPageEntityToDTO(Page<Prescription> prescriptions){
        Page<PrescriptionResponseDTO> dtoPage = prescriptions.map(new Function<Prescription, PrescriptionResponseDTO>() {
            @Override
            public PrescriptionResponseDTO apply(Prescription prescription) {
                return convertEntityToDTO(prescription);
            }
        });
        return dtoPage;
    }
}
