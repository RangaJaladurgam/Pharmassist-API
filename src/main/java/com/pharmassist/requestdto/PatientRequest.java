package com.pharmassist.requestdto;

import java.time.LocalDate;

import com.pharmassist.enums.Gender;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class PatientRequest {
	
	private String name;
	private String phoneNumber;
	private String email;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private LocalDate dateOfBirth;
	
	
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
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
	
}
