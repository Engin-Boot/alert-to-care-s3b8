package com.example.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Patient {
	@Id
	private String patient_id;
	@NotNull(message = "Name should not be null")
	@Size(min=2, message="Name should have atleast 2 characters")
	private String name;
	@NotNull(message = "DOB should not be null")
	private String dob;
	@NotNull(message = "Bed_Id should not be null")
	private String bed_id;

	@NotNull(message = "Patient_Status should not be null")
	@Enumerated(EnumType.STRING)
	private PatientStatus patientStatus;

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

	public PatientStatus getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(PatientStatus patientStatus) {
		this.patientStatus = patientStatus;
	}
}
