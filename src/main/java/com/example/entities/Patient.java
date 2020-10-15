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
	@NotNull(message = "Name should not be null")
	@Size(min=2, message="Name should have atleast 2 characters")
	private String name;
	@NotNull(message = "DOB should not be null")
	private String dob;
	@NotNull(message = "Bed_Id should not be null")
	private String bed_id;

	@NotNull(message = "Patient_Status should not be null")
    @Enum(enumClass = PatientStatus.class, ignoreCase = true, message = "Please enter correct value for PatientStatus {ADMITTED, VACANT}")
	private String patientStatus;

	public Patient(String patient_id, @NotNull(message = "Name should not be null") @Size(min = 2, message = "Name should have atleast 2 characters") String name, @NotNull(message = "DOB should not be null") String dob, @NotNull(message = "Bed_Id should not be null") String bed_id, @NotNull(message = "Patient_Status should not be null") String patientStatus) {
		this.patient_id = patient_id;
		this.name = name;
		this.dob = dob;
		this.bed_id = bed_id;
		this.patientStatus = patientStatus;
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
}
