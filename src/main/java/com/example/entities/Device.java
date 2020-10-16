package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.example.customannotations.Enum;

@Entity
public class Device {
	@Id
	private String deviceId;
	private String deviceType;
	@NotNull(message = "Device Status can not be null")
    @Enum(enumClass = DeviceStatus.class, ignoreCase = true, message = "Please enter correct value for DeviceStatus {INUSE, NOTINUSE}")
	private String deviceStatus;
	private String bedId;
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
