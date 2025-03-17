package com.pharmassist.responsedto;


public class AdminResponse {
	
	private String adminId;
	private String email;
	private String phoneNumber;
	private PharmacyResponse pharmacyResponse;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public PharmacyResponse getPharmacyResponse() {
		return pharmacyResponse;
	}
	public void setPharmacyResponse(PharmacyResponse pharmacyResponse) {
		this.pharmacyResponse = pharmacyResponse;
	}
	
	
	
}