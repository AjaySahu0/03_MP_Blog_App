package com.ait.binding;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RegistrationForm {

	@NotEmpty(message = "Name is manadatory")
	private String fname;
	private String lname;
	
	@NotEmpty(message = "Email should not be empty")
	private String email;
	private String password;

}
