package com.pharmassist.responsedto;

import com.pharmassist.enums.Gender;

public class PatientResponse {

	private String patientId;
	private String name;
	private String email;
	private Gender gender;
	private PharmacyResponse pharmacyResponse;
	
	
	
	public PharmacyResponse getPharmacyResponse() {
		return pharmacyResponse;
	}
	public void setPharmacyResponse(PharmacyResponse pharmacyResponse) {
		this.pharmacyResponse = pharmacyResponse;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
