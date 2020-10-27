package com.example.mapper;

import org.springframework.stereotype.Component;

import com.example.dto.AlertDTO;
import com.example.entities.Alert;

@Component
public class AlertMapper {

    public Alert mapAlertDTOtoAlertEntity(AlertDTO alertDTO){
        Alert alert = new Alert();
        alert.setAlertMessage(alertDTO.getAlertMessage());
        alert.setBedId(alertDTO.getBedId());
        alert.setClientId(alertDTO.getClientId());
        alert.setDeviceId(alertDTO.getDeviceId());
        alert.setPatientId(alertDTO.getPatientId());

        return alert;
    }
}
