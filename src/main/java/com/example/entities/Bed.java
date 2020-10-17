package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Bed {

    @Id
    private String bed_id;
    private String clientId;
    private String bedStatus;

    public Bed(){}

    public Bed(String bed_id, String clientId, String bedStatus) {
        this.bed_id = bed_id;
        this.clientId = clientId;
        this.bedStatus = bedStatus;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBedStatus() {
        return bedStatus;
    }

    public void setBedStatus(String bedStatus) {
        this.bedStatus = bedStatus.toUpperCase();
    }

    public String getBed_id() {
        return bed_id;
    }
    public void setBed_id(String bed_id) {
        this.bed_id = bed_id;
    }

}