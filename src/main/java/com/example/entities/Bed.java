package com.example.entities;
import javax.persistence.*;


@Entity
public class Bed {

    @Id
    private String bed_id;

    private String client_id;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Bed(){}

    public Bed(String bed_id, String client_id, Status status) {
        this.bed_id = bed_id;
        this.client_id = client_id;
        this.status = status;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getBed_id() {
        return bed_id;
    }
    public void setBed_id(String bed_id) {
        this.bed_id = bed_id;
    }

}