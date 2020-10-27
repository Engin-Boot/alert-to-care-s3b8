package com.example.mapper;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.dto.BedDTO;
import com.example.entities.Bed;

public class BedMapperTest {
	
	BedMapper bedMapper;
	
	@Before
    public void setUp() {
		bedMapper = new BedMapper();
    }
	
	@Test
	public void When_method_BedDTOtoBedEntityIsCalledThenMapWithNoException() {
		Bed bed = new Bed();
        String bedStatus = "VACANT";
        String client_id = UUID.randomUUID().toString();
		BedDTO bedDTO = new BedDTO(client_id, bedStatus);
		bed = bedMapper.mapBedDTOtoBedEntity(bedDTO);
		
		Assert.assertEquals(bed.getBedStatus(), bedDTO.getBedStatus());
		Assert.assertEquals(bed.getClientId(), bed.getClientId());
	}

}
