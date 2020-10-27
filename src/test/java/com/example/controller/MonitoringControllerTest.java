package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.dto.ClientDTO;
import com.example.dto.PatientDTO;
import com.example.dto.VitalsDTO;
import com.example.entities.Alert;
import com.example.entities.Device;
import com.example.entities.DeviceStatus;
import com.example.entities.Patient;
import com.example.entities.PatientStatus;
import com.example.repository.AlertRepository;
import com.example.repository.BedRepository;
import com.example.repository.DeviceRepository;
import com.example.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MonitoringControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    PatientRepository patientRepository;

    @Test
    void given_VitalsDTO_And_DeviceId_When_Call_Create_Alert_Api_Then_Return_201_Status() throws Exception {

        ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
        MvcResult resultClient = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andReturn();

        String clientId = JsonPath.read(resultClient.getResponse().getContentAsString(),"$.client_id");
        String bed_id = bedRepository.findByClientId(clientId).get(0).getBed_id();

        PatientDTO patientDTO = new PatientDTO("patient_name","2020-03-03",bed_id,"ADMITTED", true);

        MvcResult resultPatient = mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(patientDTO)))
        .andReturn();

        String patientId = JsonPath.read(resultPatient.getResponse().getContentAsString(),"$.patient_id");

        Device deviceAssociatedWithPatient = deviceRepository.findByBedId(bed_id);
        String device_id = deviceAssociatedWithPatient.getDeviceId();
        Map<String, Integer> measurements = new HashMap<>();
        measurements.put("spo2", 400);
        VitalsDTO vitalsDTO = new VitalsDTO(measurements);

        mockMvc.perform(post("/pms/device/{device_id}/alert",device_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(vitalsDTO)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.clientId", Matchers.is(clientId)))
                .andExpect(jsonPath("$.patientId", Matchers.is(patientId)))
                .andExpect(jsonPath("$.bedId",Matchers.is(bed_id)))
                .andExpect(jsonPath("$.deviceId",Matchers.is(device_id)))
                .andExpect(jsonPath("$.alertMessage",Matchers.is("spo2 is high ")));

    }

    @Test
    void given_VitalsDTO_And_Non_Existing_DeviceId_When_Call_Create_Alert_Api_Then_Return_400_Status() throws Exception {
        Map<String, Integer> measurements = new HashMap<>();
        measurements.put("spo2", 400);
        VitalsDTO vitalsDTO = new VitalsDTO(measurements);
        String non_existing_device_id = UUID.randomUUID().toString();

        mockMvc.perform(post("/pms/device/{device_id}/alert", non_existing_device_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(vitalsDTO)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Matchers.is("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.message", Matchers.is("Device does not exist with id = "+non_existing_device_id)));
    }

    @Test
    void given_VitalsDTO_And_DeviceId_Not_Associated_With_Any_Bed_When_Call_Create_Alert_Api_Then_Return_400_Status() throws Exception {
        Map<String, Integer> measurements = new HashMap<>();
        measurements.put("spo2", 400);
        VitalsDTO vitalsDTO = new VitalsDTO(measurements);

        String device_id = UUID.randomUUID().toString();
        Device device_not_associated_with_bed = new Device(device_id, "devicetype1", DeviceStatus.NOTINUSE.toString(), null);
        deviceRepository.save(device_not_associated_with_bed);

        mockMvc.perform(post("/pms/device/{device_id}/alert", device_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(vitalsDTO)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Matchers.is("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.message", Matchers.is("Device with id = "+device_id + " not associated with any bed")));
    }

    @Test
    void given_VitalsDTO_With_Valid_Measurements_And_DeviceId_When_Call_Create_Alert_Api_Then_Return_204_Status() throws Exception {

        ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
        MvcResult resultClient = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andReturn();

        String clientId = JsonPath.read(resultClient.getResponse().getContentAsString(),"$.client_id");
        String bed_id = bedRepository.findByClientId(clientId).get(0).getBed_id();

        PatientDTO patientDTO = new PatientDTO("patient_name","2020-03-03",bed_id,"ADMITTED", true);

        MvcResult resultPatient = mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDTO)))
                .andReturn();

        String patientId = JsonPath.read(resultPatient.getResponse().getContentAsString(),"$.patient_id");

        Device deviceAssociatedWithPatient = deviceRepository.findByBedId(bed_id);
        String device_id = deviceAssociatedWithPatient.getDeviceId();
        Map<String, Integer> measurements = new HashMap<>();
        measurements.put("spo2", 50);
        measurements.put("bpm", 70);
        VitalsDTO vitalsDTO = new VitalsDTO(measurements);

        mockMvc.perform(post("/pms/device/{device_id}/alert",device_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(vitalsDTO)))
                .andExpect(status().is(204));
    }

    @Test
    void given_Valid_PatientId_When_Call_Get_Alert_Api_Then_Return_200_Status() throws Exception {

        String patient_id = UUID.randomUUID().toString();
        Patient patient = new Patient(patient_id, "name1", "1998-04-01", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), UUID.randomUUID().toString(), true);
        patientRepository.save(patient);

        String alertMessage = "Spo2 is high";
        Alert alert1 = new Alert(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), patient_id, UUID.randomUUID().toString(), alertMessage);
        Alert alert2 = new Alert(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), patient_id, UUID.randomUUID().toString(), alertMessage);
        alertRepository.save(alert1);
        alertRepository.save(alert2);

        mockMvc.perform(get("/pms/patient/{patient_id}/alert", patient_id)
                .contentType("application/json"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void given_Valid_PatientId_When_Call_Get_Alert_Api_But_There_Are_No_Alerts_Then_Return_204_Status() throws Exception {

        String patient_id = UUID.randomUUID().toString();
        Patient patient = new Patient(patient_id, "name1", "1998-04-01", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), UUID.randomUUID().toString(), true);
        patientRepository.save(patient);

        mockMvc.perform(get("/pms/patient/{patient_id}/alert", patient_id)
                .contentType("application/json"))
                .andExpect(status().is(204));
    }

    @Test
    void given_Valid_PatientId_When_Call_Get_Alert_Api_But_Paitent_Has_Not_Subscribed_Then_Return_400_Status() throws Exception {

        String patient_id = UUID.randomUUID().toString();
        Patient patient = new Patient(patient_id, "name1", "1998-04-01", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), UUID.randomUUID().toString(), false);
        patientRepository.save(patient);

        mockMvc.perform(get("/pms/patient/{patient_id}/alert", patient_id)
                .contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Matchers.is("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.message", Matchers.is("Patient with id = " + patient_id +" Has not been subscribed to alerts")));
    }

    @Test
    void given_Alarm_Status_With_True_And_Valid_Patient_And_Client_Ids_When_Call_Change_Alarm_Status_Api_Then_Return_200_Status() throws Exception {

        String patient_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Patient patient = new Patient(patient_id, "name1", "1998-04-01", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), client_id, false);
        patientRepository.save(patient);

        Map<String, Boolean> alarmStatus = new HashMap<>();
        alarmStatus.put("isAlarmActive", true);

        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/alarmStatus", client_id, patient_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(alarmStatus)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.is("Alarm status activated")));
    }

    @Test
    void given_Alarm_Status_With_False_And_Valid_Patient_And_Client_Ids_When_Call_Change_Alarm_Status_Api_Then_Return_200_Status() throws Exception {

        String patient_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();
        Patient patient = new Patient(patient_id, "name1", "1998-04-01", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), client_id, true);
        patientRepository.save(patient);

        Map<String, Boolean> alarmStatus = new HashMap<>();
        alarmStatus.put("isAlarmActive", false);

        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/alarmStatus", client_id, patient_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(alarmStatus)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", Matchers.is("Alarm status deactivated")));
    }

    @Test
    void given_Alarm_Status_And_Non_Existing_Patient_Id_When_Call_Change_Alarm_Status_Api_Then_Return_400_Status() throws Exception {
        String non_existing_patient_id = UUID.randomUUID().toString();
        String client_id = UUID.randomUUID().toString();

        Map<String, Boolean> alarmStatus = new HashMap<>();
        alarmStatus.put("isAlarmActive", true);

        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/alarmStatus", client_id, non_existing_patient_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(alarmStatus)))
                .andExpect(jsonPath("$.code", Matchers.is("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.message", Matchers.is("Patient does not exist with id = " + non_existing_patient_id)));
    }

    @Test
    void given_Alarm_Status_And_Client_Id_Not_Associated_With_Patient_When_Call_Change_Alarm_Status_Api_Then_Return_400_Status() throws Exception {
        String patient_id = UUID.randomUUID().toString();
        String not_associated_client_id = UUID.randomUUID().toString();
        Patient patient = new Patient(patient_id, "name1", "1998-04-01", UUID.randomUUID().toString(), PatientStatus.ADMITTED.toString(), UUID.randomUUID().toString(), true);
        patientRepository.save(patient);

        Map<String, Boolean> alarmStatus = new HashMap<>();
        alarmStatus.put("isAlarmActive", true);

        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/alarmStatus", not_associated_client_id, patient_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(alarmStatus)))
                .andExpect(jsonPath("$.code", Matchers.is("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.message", Matchers.is("Patient with id = " + patient_id +" is not associated with clientId = " + not_associated_client_id )));
    }
}
