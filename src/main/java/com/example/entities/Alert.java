package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Alert {

    @Id
    private String alertId;
    private String clientId;
    private String bedId;
    private String patientId;
    private String deviceId;
    private String alertMessage;

    public Alert(){}

    public Alert(String alertId, String clientId, String bedId, String patientId, String deviceId, String alertMessage) {
        this.alertId = alertId;
        this.clientId = clientId;
        this.bedId = bedId;
        this.patientId = patientId;
        this.deviceId = deviceId;
        this.alertMessage = alertMessage;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
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