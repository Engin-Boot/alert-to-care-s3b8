package com.example.entities;

import com.example.customannotations.Enum;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Patient {
	@Id
	private String patient_id;
	private String name;
	private String dob;
	private String bed_id;
	private String patientStatus;
	private String clientId;

	public Patient(String patient_id, String name, String dob, String bed_id, String patientStatus, String clientId) {
		this.patient_id = patient_id;
		this.name = name;
		this.dob = dob;
		this.bed_id = bed_id;
		this.patientStatus = patientStatus;
		this.clientId = clientId;
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

	public String getBed_id() {
		return bed_id;
	}

	public void setBed_id(String bed_id) {
		this.bed_id = bed_id;
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
}
