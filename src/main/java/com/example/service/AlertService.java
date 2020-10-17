package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.AlertDTO;
import com.example.dto.VitalsDTO;
import com.example.entities.Alert;
import com.example.mapper.AlertMapper;
import com.example.repository.AlertRepository;
import com.example.vitalactions.VitalResolver;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class AlertService {
    AlertRepository alertRepository;

    AlertMapper alertMapper;

    @Autowired
    public AlertService(AlertRepository alertRepository, AlertMapper alertMapper) {
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
    }

    public Alert saveAlert(String bed_id, String client_id, String patient_id, String device_id, VitalsDTO vitalsDTO) throws JsonProcessingException {

        String vitalsStatus = checkVitalsAreOk(vitalsDTO.getMeasurements());
        if(!vitalsStatus.equals("")) {
            AlertDTO alertDTO = new AlertDTO(client_id, bed_id, patient_id, device_id, vitalsStatus);
            Alert alert = alertMapper.mapAlertDTOtoAlertEntity(alertDTO);
            String alert_id = UUID.randomUUID().toString();
            alert.setAlertId(alert_id);

            return alertRepository.save(alert);
        }
        return null;
    }

    public List<Alert> getAllAlertsByPatientId(String patient_id){
        List<Alert> alerts = alertRepository.findByPatientId(patient_id);
        return  alerts;
    }

    public String checkVitalsAreOk(Map<String, Integer> measurement) throws JsonProcessingException{
        String message = "";
        if(measurement.isEmpty()) {
        	return "Device is malfunctioning";
        }
        message = VitalResolver.vitalResolver(measurement);
        System.out.println(message);
        return message;
    }
}
