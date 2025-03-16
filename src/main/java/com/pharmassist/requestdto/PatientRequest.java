package com.pharmassist.requestdto;

import java.time.LocalDate;

import com.pharmassist.enums.Gender;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PatientRequest {
	
	@NotBlank(message = "Patient Name cannot be Blank")
	@NotNull(message = "Patient Name cannot be null")
	@Pattern(regexp = "^[A-Za-z\\s]{2,}$",message = "Name must contain only letters and spaces, with a minimum of 2 characters.")
	private String name;
	
	@NotBlank(message = "Phone Number cannot be Blank")
	@NotNull(message = "Phone Number cannot be null")
	@Pattern(regexp = "^[0-9]{10}$",message = "Phone number must be exactly 10 digits." )
	private String phoneNumber;
	
	@NotBlank(message = "Email cannot be blank")
	@NotNull(message = "Email cannot be null")
	@Email(regexp = "^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$",message = "Email must be in a valid format, e.g., example@example.com.")
	private String email;
	

	private Gender gender;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	
	
}
