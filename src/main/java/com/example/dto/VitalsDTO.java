package com.example.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;


public class VitalsDTO {
	
	private Map<String,String> measurements;

	public VitalsDTO(Map<String, String> measurements) {
		super();
		this.measurements = measurements;
	}

	public VitalsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Map<String, String> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(Map<String, String> measurements) {
		this.measurements = measurements;
	}
	
	
//	
//	public VitalsDTO(String measurements) {
//		this.measurements = measurements;
//	}
//
//	public VitalsDTO() {}
//	
//	public String getMeasurements() {
//		return measurements;
//	}
//
//	public void setMeasurements(String measurements) {
//		this.measurements = measurements;
//	}
//


//    private int spo2;
//
//    private int bpm;
//
//    private int respRate;
//
//    public VitalsDTO(){}
//
//    public VitalsDTO(int spo2, int bpm, int respRate) {
//        this.spo2 = spo2;
//        this.bpm = bpm;
//        this.respRate = respRate;
//    }
//
//    public int getSpo2() {
//        return spo2;
//    }
//
//    public void setSpo2(int spo2) {
//        this.spo2 = spo2;
//    }
//
//    public int getBpm() {
//        return bpm;
//    }
//
//    public void setBpm(int bpm) {
//        this.bpm = bpm;
//    }
//
//    public int getRespRate() {
//        return respRate;
//    }
//
//    public void setRespRate(int respRate) {
//        this.respRate = respRate;
//    }
}