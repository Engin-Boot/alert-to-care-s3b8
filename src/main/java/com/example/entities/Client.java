package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Client {
	@Id
	private String client_id;
	private String client_type;
	private String layout;
	private int no_of_beds;

	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public int getNo_of_beds() {
		return no_of_beds;
	}
	public void setNo_of_beds(int no_of_beds) {
		this.no_of_beds = no_of_beds;
	}
	
	
}
