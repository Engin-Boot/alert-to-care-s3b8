package com.example.mapper;

import com.example.dto.DeviceDTO;
import com.example.entities.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
	public static Device mapDeviceDTOtoDeviceEntity(DeviceDTO deviceDTO) {
		Device device = new Device();
		device.setBedId(deviceDTO.getBedId());
		device.setDeviceId(deviceDTO.getDeviceId());
		device.setDeviceStatus(deviceDTO.getDeviceStatus());
		device.setDeviceType(deviceDTO.getDeviceType());

		return device;
	}
	
}
