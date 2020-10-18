package com.example.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.dto.ClientDTO;
import com.example.entities.Client;

public class ClientMapperTest {
	
	ClientMapper clientMapper;
	
	@Before
    public void setUp() {
		clientMapper = new ClientMapper();
    }
	
	@Test
	public void When_method_mapClientDTOtoClientEntityIsCalledThenMapWithNoException() {
		Client client = new Client();
		String clientType = "type1";
		int no_of_beds = 5;
        String layout = "PARALLEL";
		ClientDTO clientDTO = new ClientDTO(clientType, layout, no_of_beds);
		client = clientMapper.mapClientDTOtoClientEntity(clientDTO);
		Assert.assertEquals(client.getClient_type(), clientDTO.getClient_type());
		Assert.assertEquals(client.getLayout(), clientDTO.getLayout());
		Assert.assertEquals(client.getNo_of_beds(), clientDTO.getNo_of_beds());

	}

}
