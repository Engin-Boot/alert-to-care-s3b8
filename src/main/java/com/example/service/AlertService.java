package com.example.service;

import com.example.dto.AlertDTO;
import com.example.dto.BedDTO;
import com.example.dto.ClientDTO;
import com.example.dto.VitalsDTO;
import com.example.entities.Alert;
import com.example.entities.Bed;
import com.example.entities.BedStatus;
import com.example.entities.Client;
import com.example.exceptions.BedDoesNotBelongToSpecifiedClientException;
import com.example.exceptions.BedDoesNotExistException;
import com.example.exceptions.BedHasAlreadyBeenOccupiedException;
import com.example.exceptions.ClientAlreadyExistsException;
import com.example.mapper.AlertMapper;
import com.example.mapper.BedMapper;
import com.example.repository.AlertRepository;
import com.example.repository.BedRepository;
import com.example.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AlertService {
    AlertRepository alertRepository;

    AlertMapper alertMapper;

    @Autowired
    public AlertService(AlertRepository alertRepository, AlertMapper alertMapper) {
        this.alertRepository = alertRepository;
        this.alertMapper = alertMapper;
    }

    public Alert saveAlert(String bed_id, String client_id, String patient_id, String device_id, VitalsDTO vitalsDTO) {

        String vitalsStatus = checkVitalsAreOk(vitalsDTO);
        if(!vitalsStatus.equals("")) {
            AlertDTO alertDTO = new AlertDTO(client_id, bed_id, patient_id, device_id, vitalsStatus);
            Alert alert = alertMapper.mapAlertDTOtoAlertEntity(alertDTO);
            String alert_id = UUID.randomUUID().toString();
            alert.setAlertId(alert_id);

            return alertRepository.save(alert);
        }
        return null;
    }





    public String checkVitalsAreOk(VitalsDTO vitalsDTO){
        String message = "";
        message += checkIfSpo2Ok(vitalsDTO.getSpo2());
        message +=checkIfBpmOk(vitalsDTO.getBpm());
        message += checkIfResprateOk(vitalsDTO.getRespRate());
        return message;
    }

    public String checkIfSpo2Ok(int value){
        String message = "";
        message = checkLimit(Integer.parseInt(Utility.getVitalLimit("spo2low")), Integer.parseInt(Utility.getVitalLimit("spo2high")), value, "Spo2");
        return message;
    }
    public String checkIfBpmOk(int value){
        String message = "";
        message = checkLimit(Integer.parseInt(Utility.getVitalLimit("bpmlow")), Integer.parseInt(Utility.getVitalLimit("bpmhigh")), value, "BPM");
        return message;
    }

    public String checkIfResprateOk(int value){
        String message = "";
        message = checkLimit(Integer.parseInt(Utility.getVitalLimit("respratelow")), Integer.parseInt(Utility.getVitalLimit("respratehigh")), value, "RespRate");
        return message;
    }

    public String checkLimit (int low, int high, int value, String vitalName){
        if(value<low){
            return vitalName+" too Low\n";
        }
        else if(value>high){
            return vitalName+ "too high\n";
        }
        else{
            return "";
        }
    }





}
