package com.example.mapper;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.dto.DeviceDTO;
import com.example.entities.Device;

public class DeviceMapperTest {
	
	DeviceMapper deviceMapper;
	
	@Before
    public void setUp() {
		deviceMapper = new DeviceMapper();
    }
	
	@Test
	public void When_method_mapDeviceDTOtoDeviceEntityIsCalledThenMapWithNoException() {
		Device device = new Device();
        String bed_id = UUID.randomUUID().toString();
        String device_id = UUID.randomUUID().toString();
        String deviceStatus = "INUSE";
        String deviceType = "type1";
        
		DeviceDTO deviceDTO = new DeviceDTO(device_id, deviceType, deviceStatus, bed_id );
		device = deviceMapper.mapDeviceDTOtoDeviceEntity(deviceDTO);
		Assert.assertEquals(device.getDeviceStatus(), deviceDTO.getDeviceStatus());
		Assert.assertEquals(device.getDeviceType(), deviceDTO.getDeviceType());
		Assert.assertEquals(device.getBedId(), deviceDTO.getBedId());
		Assert.assertEquals(device.getDeviceId(), deviceDTO.getDeviceId());
	}

}
