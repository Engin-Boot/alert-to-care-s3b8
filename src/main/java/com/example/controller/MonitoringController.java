package com.example.controller;

import com.example.dto.VitalsDTO;
import com.example.entities.Alert;
import com.example.entities.Bed;
import com.example.entities.Device;
import com.example.entities.Patient;
import com.example.exceptions.*;
import com.example.service.AlertService;
import com.example.service.BedService;
import com.example.service.DeviceService;
import com.example.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/pms")
public class MonitoringController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BedService bedService;

    @Autowired
    private AlertService alertService;

    @PostMapping("/device/{device_id}/alert")
    public ResponseEntity<Alert> createAlert(@PathVariable(value = "device_id") UUID device_id, @RequestBody VitalsDTO measurement) throws DeviceDoesNotExistException, BedDoesNotExistException, PatientDoesNotExistException, DeviceNotAssociatedWithBedException, JsonProcessingException, PatientHasNotSubscribedException {
        //GET DEVICE FROM ID FIRST. FROM DEVICE EXTRACT BED_ID. THEN GET BED FROM THE BED-ID.GET CLIENT_ID FROM THE BED. THEN GET PATIENT USING BED_ID
        Device device = deviceService.getDevice(device_id.toString());
        String bed_id = device.getBedId();

        Bed bed = bedService.getBed(bed_id);
        String client_id = bed.getBed_id();

        Patient patient = patientService.getPatientByBedId(bed_id);
        String patient_id = patient.getPatient_id();

        Alert savedAlert = alertService.saveAlert(bed_id,client_id,patient_id,device_id.toString(), measurement);
        if(savedAlert == null){
            return new ResponseEntity<Alert>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Alert>(savedAlert, HttpStatus.CREATED);
    }
    
   
    @GetMapping("/patient/{patient_id}/alert")
    public ResponseEntity<List<Alert>> getAlerts(@PathVariable(value = "patient_id") UUID patient_id) throws PatientHasNotSubscribedException, PatientDoesNotExistException  {
        Patient patient = patientService.getPatient(patient_id.toString());

        if(patient.getIsAlarmActive()) {
            List<Alert> alerts = alertService.getAllAlertsByPatientId(patient_id.toString());
            if(alerts.size() == 0){
                return new ResponseEntity<List<Alert>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Alert>>(alerts, HttpStatus.OK);
        }
        else{
            throw new PatientHasNotSubscribedException("Patient with id = "+patient_id + " Has not been subscribed to alerts");
        }
    }

    @PutMapping("/client/{client_id}/patient/{patient_id}/alarmStatus")
    public ResponseEntity<String> changeAlertStatus(@PathVariable(value = "client_id") UUID client_id, @PathVariable(value = "patient_id") UUID patient_id, @Valid @RequestBody Map<String, Boolean> isAlarmActive) throws PatientDoesNotBelongToSpecifiedClientException, PatientDoesNotExistException {

        Patient updatedPatient = patientService.changeAlarmStatus(isAlarmActive.get("isAlarmActive"), patient_id.toString(), client_id.toString());
        String alarmStatus = "";
        if(updatedPatient.getIsAlarmActive()) {
            alarmStatus = "Alarm status activated";
        }
        else {
            alarmStatus = "Alarm status deactivated";
        }
        return new ResponseEntity<String>(alarmStatus, HttpStatus.OK);
    }
}
