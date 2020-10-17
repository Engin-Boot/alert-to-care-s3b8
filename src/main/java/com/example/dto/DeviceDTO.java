package com.example.dto;

import com.example.customannotations.Enum;
import com.example.entities.DeviceStatus;

import javax.validation.constraints.NotNull;

public class DeviceDTO {
	
	private String deviceId;
	@NotNull(message = "DeviceType can not be null")
	private String deviceType;
	@NotNull(message = "Device Status can not be null")
    @Enum(enumClass = DeviceStatus.class, ignoreCase = true, message = "Please enter correct value for DeviceStatus {INUSE, NOTINUSE}")
	private String deviceStatus;
	private String bedId;

	public DeviceDTO(){}

	public DeviceDTO(String deviceId, @NotNull(message = "DeviceType can not be null") String deviceType, @NotNull(message = "Device Status can not be null") String deviceStatus, String bedId) {
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.deviceStatus = deviceStatus;
		this.bedId = bedId;
	}

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getBedId() {
		return bedId;
	}
	public void setBedId(String bedId) {
		this.bedId = bedId;
	}

}
