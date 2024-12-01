package com.pharmassist.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AdminRequest {
	
	@NotBlank(message = "email cannot be blank")
	@NotNull(message = "email cannot be null")
	@Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$"
		  ,message = "Invalid Gmail Id")
	private String email;
	
	@NotNull(message = "phone number cannot be null")
	@NotBlank(message = "phone number cannot be blank")
	@Pattern(regexp = "^[6-9]\\d{9}$"
			,message = "Invalid phone number")
	private String phoneNumber;
	
	@NotNull(message = "password cannot be null")
	@NotBlank(message = "password cannot be blank")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,12}$"
		    ,message = "password must have at least one uppercase letter, one lowercase letter, one number, one special character, and a length between 8 and 12 characters")
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
