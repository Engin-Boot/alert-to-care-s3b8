package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Patient {
	@Id
	private String patient_id;
	private String name;
	private String dob;
	private String bedId;
	private String patientStatus;
	private String clientId;
	private boolean isAlarmActive;

	public Patient(String patient_id, String name, String dob, String bedId, String patientStatus, String clientId, boolean isAlarmActive) {
		this.patient_id = patient_id;
		this.name = name;
		this.dob = dob;
		this.bedId = bedId;
		this.patientStatus = patientStatus;
		this.clientId = clientId;
		this.isAlarmActive = isAlarmActive;
	}

	public Patient() {

    }

	public String getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBedId() {
		return bedId;
	}
	public void setBedId(String bedId) {
		this.bedId = bedId;
	}

	public String getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus.toUpperCase();
	}

	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public boolean getIsAlarmActive() {
		return isAlarmActive;
	}

	public void setIsAlarmActive(boolean isAlarmActive) {
		this.isAlarmActive = isAlarmActive;
	}
}
