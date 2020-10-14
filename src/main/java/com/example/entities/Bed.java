package com.example.entities;

import javax.persistence.Entity;

@Entity
public class Bed {
	
	private String bed_id;
	private enum status{
		occupied, vacant, maintenance
	}
	
	public String getBed_id() {
		return bed_id;
	}
	public void setBed_id(String bed_id) {
		this.bed_id = bed_id;
	};
	
	
}
