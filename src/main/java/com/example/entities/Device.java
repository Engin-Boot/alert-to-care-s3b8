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
	private String deviceStatus;
	private String bedId;

	public Device(){}

	public Device(String deviceId, String deviceType, String deviceStatus, String bedId) {
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
