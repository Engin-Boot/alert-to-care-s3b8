package com.example.dto;

import javax.validation.constraints.NotNull;


public class AlertDTO {

    @NotNull(message = "clientId should not be null")
    private String clientId;
    @NotNull(message = "bedId should not be null")
    private String bedId;
    @NotNull(message = "patientId should not be null")
    private String patientId;
    @NotNull(message = "deviceId should not be null")
    private String deviceId;
    @NotNull(message = "alertMessage should not be null")
    private String alertMessage;

    public AlertDTO(){}

    public AlertDTO(@NotNull(message = "clientId should not be null") String clientId, @NotNull(message = "bedId should not be null") String bedId, @NotNull(message = "patientId should not be null") String patientId, @NotNull(message = "deviceId should not be null") String deviceId, @NotNull(message = "alertMessage should not be null") String alertMessage) {
        this.clientId = clientId;
        this.bedId = bedId;
        this.patientId = patientId;
        this.deviceId = deviceId;
        this.alertMessage = alertMessage;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }
}