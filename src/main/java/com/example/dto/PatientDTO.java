package com.example.dto;

import com.example.customannotations.Enum;
import com.example.entities.PatientStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PatientDTO {

	@NotNull(message = "Name should not be null")
	@Size(min=2, message="Name should have atleast 2 characters")
	private String name;
	@NotNull(message = "DOB should not be null")
	private String dob;
	@NotNull(message = "Bed_Id should not be null")
	private String bed_id;

    @Enum(enumClass = PatientStatus.class, ignoreCase = true, message = "Please enter correct value for PatientStatus {ADMITTED, VACANT}")
	private String patientStatus;

	public PatientDTO(@NotNull(message = "Name should not be null") @Size(min = 2, message = "Name should have atleast 2 characters") String name, @NotNull(message = "DOB should not be null") String dob, @NotNull(message = "Bed_Id should not be null") String bed_id, @NotNull(message = "Patient_Status should not be null") String patientStatus) {
		this.name = name;
		this.dob = dob;
		this.bed_id = bed_id;
		this.patientStatus = patientStatus;
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
