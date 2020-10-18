package com.example.controller;

import com.example.dto.ClientDTO;
import com.example.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConfigurationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientService clientService;

    @Test
    void given_ClientDTO_When_Call_Create_Client_Api_Then_Return_201_Status() throws Exception {
        ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);

        mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.client_type", Matchers.is("typ1")))
                .andExpect(jsonPath("$.layout", Matchers.is("DEFAULT")))
                .andExpect(jsonPath("$.no_of_beds", Matchers.is(3)));

//        mockMvc.perform(post("/api/savePatient")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(patient)))
//                .andExpect(status().is(201));
//
//        Patient savedPatient = patientRepository.findById(1).get();
//        assertEquals("email", savedPatient.getPatient_email());
//        assertEquals("name", savedPatient.getPatient_name());
    }

    @Test
    void given_ClientDTO_With_Missing_Client_Type_When_Call_Create_Client_Api_Then_Return_400_Status() throws Exception {
        ClientDTO clientDTO = new ClientDTO(null, "DEFAULT", 3);

        mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Matchers.is("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.message", Matchers.is("Validation failed for request body: Client_Type should not be null, ")));

    }

    @Test
    void given_ClientDTO_When_Call_Create_Client_Api_Then_Return_xx_Status() throws Exception {
        ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);

        mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.client_type", Matchers.is("typ1")))
                .andExpect(jsonPath("$.layout", Matchers.is("DEFAULT")))
                .andExpect(jsonPath("$.no_of_beds", Matchers.is(3)));
    }
    

}
