package com.example.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.dto.AlertDTO;
import com.example.dto.VitalsDTO;
import com.example.entities.Alert;
import com.example.mapper.AlertMapper;
import com.example.repository.AlertRepository;

@RunWith(MockitoJUnitRunner.class)
public class AlertServiceTest {

    @Mock
    AlertRepository alertRepository;

    @Mock
    AlertMapper alertMapper;


    AlertService alertService;

    @Captor
    ArgumentCaptor<Alert> alertArgumentCaptor;

    @Before
    public void setUp() {
        alertService = new AlertService(alertRepository, alertMapper);
    }

    @Test
    public void given_Vitals_Out_Of_Range_When_Creating_Alert_Then_Throw_No_Exception() {
        String alert_id =UUID.randomUUID().toString();
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        String patient_id = UUID.randomUUID().toString();
        String device_id = UUID.randomUUID().toString();
        String msg = "spo2 is high";

        Alert alert = new Alert(alert_id, client_id,bed_id,patient_id,device_id, msg);
        AlertDTO alertDTO = new AlertDTO(client_id,bed_id,patient_id,device_id, msg);
        Map<String, Integer> measurements = new HashMap<>();
        measurements.put("spo2", 400);

        VitalsDTO vitalsDTO = new VitalsDTO(measurements);

        when(alertMapper.mapAlertDTOtoAlertEntity(any())).thenReturn(alert);
        when(alertRepository.save(any())).thenReturn(alert);

        alertService.saveAlert(bed_id, client_id, patient_id, device_id, vitalsDTO );

        verify(alertRepository).save(alertArgumentCaptor.capture());

        Assert.assertEquals(alertDTO.getBedId(), alertArgumentCaptor.getValue().getBedId());
        Assert.assertEquals(alertDTO.getClientId(), alertArgumentCaptor.getValue().getClientId());
        Assert.assertEquals(alertDTO.getDeviceId(), alertArgumentCaptor.getValue().getDeviceId());
        Assert.assertEquals(alertDTO.getPatientId(), alertArgumentCaptor.getValue().getPatientId());
        Assert.assertEquals(alertDTO.getAlertMessage(), alertArgumentCaptor.getValue().getAlertMessage());
    }

    @Test
    public void given_Vitals_Within_Range_When_Creating_Alert_Then_Return_Null() {
        String alert_id =UUID.randomUUID().toString();
        String bed_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        String patient_id = UUID.randomUUID().toString();
        String device_id = UUID.randomUUID().toString();
        String msg = "";

        Alert alert = new Alert(alert_id, client_id,bed_id,patient_id,device_id, msg);
        AlertDTO alertDTO = new AlertDTO(client_id,bed_id,patient_id,device_id, msg);
        Map<String, Integer> measurements = new HashMap<>();
        measurements.put("spo2", 60);

        VitalsDTO vitalsDTO = new VitalsDTO(measurements);

        Alert savedAlert = alertService.saveAlert(bed_id, client_id, patient_id, device_id, vitalsDTO );

        verify(alertRepository, times(0)).save(any());

        Assert.assertNull(savedAlert);
    }

    @Test
    public void given_PatientId_When_Get_All_Alerts_By_Patient_Id_Then_Throw_No_Exception(){
        String patient_id = UUID.randomUUID().toString();
        List<Alert> alerts = new ArrayList<>();
        alerts.add(new Alert(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), patient_id, UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        when(alertRepository.findByPatientId(any())).thenReturn(alerts);

        List<Alert> savedAlerts = alertService.getAllAlertsByPatientId(patient_id);
        Assert.assertEquals(1, savedAlerts.size());
    }

}


