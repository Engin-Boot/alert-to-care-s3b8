package com.example.mapper;

import com.example.dto.DeviceDTO;
import com.example.entities.Device;

public class DeviceMapper {
	public static void mapDeviceDTOtoDevice(DeviceDTO devicedto) {		
	Device device = new Device();
	device.setBedId(devicedto.getBedId());
	device.setDeviceId(devicedto.getDeviceId());
	device.setDeviceStatus(devicedto.getDeviceStatus());
	device.setDeviceType(devicedto.getDeviceType());
	}
	
}
