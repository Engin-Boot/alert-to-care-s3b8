package com.example.mapper;

import com.example.dto.BedDTO;
import com.example.dto.PatientDTO;
import com.example.entities.Bed;
import com.example.entities.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public Patient mapPatientDTOtoPatientEntity(PatientDTO patientDTO){
        Patient patient = new Patient();
        patient.setBed_id(patientDTO.getBed_id());
        patient.setDob(patientDTO.getDob());
        patient.setName(patientDTO.getName());
        if(patientDTO.getPatientStatus()!=null){
            patient.setPatientStatus(patientDTO.getPatientStatus());
        }

        return patient;
    }
}
