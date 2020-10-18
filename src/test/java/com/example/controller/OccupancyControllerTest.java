package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import com.example.dto.ClientDTO;
import com.example.dto.PatientDTO;
import com.example.entities.Bed;
import com.example.repository.BedRepository;
import com.example.service.ClientService;
import com.example.service.PatientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OccupancyControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientService patientService;
    
    @Autowired
    private  BedRepository bedrepository;
    
    @Test
    void given_PatientDTO_When_Call_Create_Patient_Api_Then_Return_201_Status() throws Exception {

    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");
        Bed bed = bedrepository.findByClientId(clientId).get(0);
        String bed_id = bed.getBed_id();
        
        PatientDTO patientDto = new PatientDTO("test","2020-03-03",bed_id,"ADMITTED", true);

        mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.name", Matchers.is("test")))
                .andExpect(jsonPath("$.dob", Matchers.is("2020-03-03")))
                .andExpect(jsonPath("$.bedId", Matchers.is(bed_id)))
                .andExpect(jsonPath("$.patientStatus",Matchers.is("ADMITTED")))
                .andExpect(jsonPath("$.isAlarmActive",Matchers.is(true)));
    }
    
    @Test
    void given_PatientDTO_When_Call_Create_Patient_Api_with_wrong_format_dob_Return_400_Status_with_message() throws Exception {

    
        
        PatientDTO patientDto = new PatientDTO("test","2-03-03",UUID.randomUUID().toString(),"ADMITTED", true);

        mockMvc.perform(post("/pms/client/{clientId}/patient",UUID.randomUUID().toString())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", Matchers.is("DOB is not present in the yyyy-MM-dd format")));
               
    }
    
    @Test
    void given_PatientDTO_When_Call_Create_Patient_Api_with_wrong_bed_id_then_Return_400_Status_with_message() throws Exception {

    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");
        
        String invalidBedId = UUID.randomUUID().toString();
        
        
        PatientDTO patientDto = new PatientDTO("test","2020-03-03",invalidBedId,"ADMITTED", true);

        mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", Matchers.is("Bed with id = "+invalidBedId+" does not exist")));        
    }
    
    @Test
    void given_PatientDTO_When_Call_Create_Patient_Api_with_wrong_client_id_then_Return_400_Status_with_message() throws Exception {

    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");
        Bed bed = bedrepository.findByClientId(clientId).get(0);
        String bed_id = bed.getBed_id();
        
        String invalidClientId = UUID.randomUUID().toString();
        
        PatientDTO patientDto = new PatientDTO("test","2020-03-03",bed_id,"ADMITTED", true);

        mockMvc.perform(post("/pms/client/{clientId}/patient",invalidClientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", Matchers.is("Bed with id ="+bed_id+ " is not associated with clientId="+invalidClientId)));        
    }
    
    @Test
    void given_PatientDTO_When_Call_Create_Patient_Api_with_already_occupied_bed_then_Return_400_Status_with_message() throws Exception {

    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");
        Bed bed = bedrepository.findByClientId(clientId).get(0);
        String bed_id = bed.getBed_id();
        PatientDTO patientDto1 = new PatientDTO("test","2020-03-03",bed_id,"ADMITTED", true);

        mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto1)));
        
        PatientDTO patientDto2 = new PatientDTO("test1","2020-03-03",bed_id,"ADMITTED", true);
        mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto2)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", Matchers.is("Bed with id = "+bed_id+" has already been occupied")));                
                        
    }
    
    @Test
    public void given_beds_when_getAllBedStatusForSpecifiedClient_api_is_called_then_return_status_200() throws JsonProcessingException, Exception{
    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");
        
        mockMvc.perform(get("/pms/client/{client_id}/bed",clientId)
                .contentType("application/json"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", org.hamcrest.collection.IsMapWithSize.aMapWithSize(3)));
       
    }
    
    @Test
    public void given_beds_when_getAllBedStatusForSpecifiedClient_api_is_called_with_invalid_clientId_then_return_status_400_with_message() throws JsonProcessingException, Exception{
    	String clientId = UUID.randomUUID().toString();
    	
        mockMvc.perform(get("/pms/client/{client_id}/bed",clientId)
                .contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", Matchers.is("Client with id = "+clientId+ "does not exist")));
    }
    
    @Test
    public void given_beds_when_dischargepatient_api_is_called_then_return_status_200() throws Exception{
    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
    	
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");
        Bed bed = bedrepository.findByClientId(clientId).get(0);
        String bed_id = bed.getBed_id();
        PatientDTO patientDto1 = new PatientDTO("test","2020-03-03",bed_id,"ADMITTED", true);

        MvcResult patientResult = mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto1)))
        		.andReturn();
        
        String patientId = JsonPath.read(patientResult.getResponse().getContentAsString(),"$.patient_id");
        
        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/discharge",clientId, patientId)
                .contentType("application/json"))
                .andExpect(status().is(200));	
    }
    
    @Test
    public void given_beds_when_dischargepatient_api_is_called_with_wrong_patient_id_then_return_status_400_with_message() throws Exception{
    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
    	
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");       
        String patientId = UUID.randomUUID().toString();
        
        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/discharge",clientId, patientId)
                .contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", Matchers.is("Patient does not exist with id = "+patientId)));
    }
    
    @Test
    public void given_beds_when_dischargepatient_api_is_called_with_wrong_client_id_then_return_status_400_with_message() throws Exception{
    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
    	
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");
        Bed bed = bedrepository.findByClientId(clientId).get(0);
        String bed_id = bed.getBed_id();
        PatientDTO patientDto1 = new PatientDTO("test","2020-03-03",bed_id,"ADMITTED", true);

        MvcResult patientResult = mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto1)))
        		.andReturn();
        
        String patientId = JsonPath.read(patientResult.getResponse().getContentAsString(),"$.patient_id");
        String invalidClientId = UUID.randomUUID().toString();

        
        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/discharge",invalidClientId, patientId)
                .contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", Matchers.is("Patient with id = "+patientId+" is not associated with clientId = "+invalidClientId)));
    }
    
    @Test
    public void given_beds_when_dischargepatient_api_is_called_for_already_discharged_patient_then_return_status_400() throws Exception{
    	ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);
    	
        MvcResult result = mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO))).andReturn();
        String clientId = JsonPath.read(result.getResponse().getContentAsString(),"$.client_id");
        Bed bed = bedrepository.findByClientId(clientId).get(0);
        String bed_id = bed.getBed_id();
        PatientDTO patientDto1 = new PatientDTO("test","2020-03-03",bed_id,"ADMITTED", true);

        MvcResult patientResult = mockMvc.perform(post("/pms/client/{clientId}/patient",clientId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(patientDto1)))
        		.andReturn();
        
        String patientId = JsonPath.read(patientResult.getResponse().getContentAsString(),"$.patient_id");
        
        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/discharge",clientId, patientId)
                .contentType("application/json"));
        mockMvc.perform(put("/pms/client/{client_id}/patient/{patient_id}/discharge",clientId, patientId)
                .contentType("application/json"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.message", Matchers.is("Patient with id = "+patientId+" has already been discharged")));
    }
}
