package com.example.controller;

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

import com.example.dto.ClientDTO;
import com.example.entities.Client;
import com.example.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    void given_ClientDTO_With_Zero_Beds_When_Call_Create_Client_Api_Then_Return_400_Status() throws Exception {
        ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 0);

        mockMvc.perform(post("/pms/client/config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code", Matchers.is("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.message", Matchers.is("Validation failed for request body: Min no of beds should be greater or equal to 1, ")));

    }

    @Test
    void given_New_ClientDTO_When_Call_Update_Client_Api_Then_Client_Created_With_Return_200_Status() throws Exception {
        ClientDTO clientDTO = new ClientDTO("typ1", "DEFAULT", 3);

        String client_id = UUID.randomUUID().toString();

        mockMvc.perform(put("/pms/client/{client_id}/config", client_id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.client_type", Matchers.is("typ1")))
                .andExpect(jsonPath("$.layout", Matchers.is("DEFAULT")))
                .andExpect(jsonPath("$.no_of_beds", Matchers.is(3)));
    }

    @Test
    void given_ClientDTO_With_Existing_Id_When_Call_Update_Client_Api_Then_Client_Updated_With_Return_200_Status() throws Exception {
        ClientDTO existingClientDTO = new ClientDTO("typ1", "DEFAULT", 3);
        Client existingClient = clientService.saveClient(existingClientDTO);
        int no_of_beds_to_add = 5;
        ClientDTO clientDTO = new ClientDTO("typ2", "L_LAYOUT", no_of_beds_to_add);

        String client_id = UUID.randomUUID().toString();

        mockMvc.perform(put("/pms/client/{client_id}/config", existingClient.getClient_id())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.client_type", Matchers.is("typ2")))
                .andExpect(jsonPath("$.layout", Matchers.is("L_LAYOUT")))
                .andExpect(jsonPath("$.no_of_beds", Matchers.is(3 + no_of_beds_to_add)));
    }
}
