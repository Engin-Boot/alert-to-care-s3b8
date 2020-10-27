package com.example.mapper;

import org.springframework.stereotype.Component;

import com.example.dto.PatientDTO;
import com.example.entities.Patient;

@Component
public class PatientMapper {

    public Patient mapPatientDTOtoPatientEntity(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setBedId(patientDTO.getBed_id());
        patient.setDob(patientDTO.getDob());
        patient.setName(patientDTO.getName());
        if(patientDTO.getPatientStatus()!=null){
            patient.setPatientStatus(patientDTO.getPatientStatus());
        }
        patient.setIsAlarmActive(patientDTO.getIsAlarmActive());

        return patient;
    }
}
