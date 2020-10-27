package com.example.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.customannotations.Enum;
import com.example.entities.BedLayout;

public class ClientDTO {
	@NotNull(message = "Client_Type should not be null")
	@Size(min=2, message="Client_Type should have atleast 2 characters")
	private String client_type;
	@NotNull(message = "Layout should not be null")
	@Enum(enumClass = BedLayout.class, ignoreCase = true, message = "Please enter correct value for Layout {Default, L_Layout, Parallel")
	private String layout;
	@NotNull
	@Min(value = 1, message = "Min no of beds should be greater or equal to 1")
	private int no_of_beds;

	public ClientDTO(){}

	public ClientDTO(@NotNull(message = "Client_Type should not be null") @Size(min = 2, message = "Client_Type should have atleast 2 characters") String client_type, @NotNull(message = "Layout should not be null") String layout, @NotNull @Min(value = 1, message = "Min no of beds should be greater or equal to 1") int no_of_beds) {
		this.client_type = client_type;
		this.layout = layout;
		this.no_of_beds = no_of_beds;
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
