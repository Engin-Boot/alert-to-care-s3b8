package com.example.mapper;

import org.springframework.stereotype.Component;

import com.example.dto.ClientDTO;
import com.example.entities.Client;

@Component
public class ClientMapper {

    public Client mapClientDTOtoClientEntity(ClientDTO clientDTO){
        Client client = new Client();
        client.setClient_type(clientDTO.getClient_type());
        client.setNo_of_beds(clientDTO.getNo_of_beds());
        client.setLayout(clientDTO.getLayout());

        return client;
    }
}
