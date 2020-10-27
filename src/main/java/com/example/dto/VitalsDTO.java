package com.example.dto;

import java.util.Map;

import lombok.Data;

@Data
public class VitalsDTO {

	private Map<String,Integer> measurement;

	public VitalsDTO(Map<String, Integer> measurement) {
		super();
		this.measurement= measurement;
	}

	public VitalsDTO() {
		super();
	}
	
	public Map<String, Integer> getMeasurements() {
		return measurement;
	}

	public void setMeasurements(Map<String, Integer> measurements) {
		this.measurement = measurements;
	}
}