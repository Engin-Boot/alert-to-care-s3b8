package com.example.dto;

import javax.validation.constraints.NotNull;


public class VitalsDTO {

    private int spo2;

    private int bpm;

    private int respRate;

    public VitalsDTO(){}

    public VitalsDTO(int spo2, int bpm, int respRate) {
        this.spo2 = spo2;
        this.bpm = bpm;
        this.respRate = respRate;
    }

    public int getSpo2() {
        return spo2;
    }

    public void setSpo2(int spo2) {
        this.spo2 = spo2;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public int getRespRate() {
        return respRate;
    }

    public void setRespRate(int respRate) {
        this.respRate = respRate;
    }
}