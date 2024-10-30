package com.pharmassist.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PharmacyRequest {
	
	@NotBlank(message = "Name cannot be blank")
	@NotNull(message = "Name cannot be null")
	@Pattern(regexp = "^[a-zA-Z]$")
	private String name;
	
	
	@NotBlank(message = "GST No. cannot be blank")
	@NotNull(message = "GST No. cannot be null")
	@Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[A-Z0-9]{3}$",
			message = "Invalid GST number. A GST number should be 15 characters long,"
					+ " starting with 2 digits followed by 10 alphanumeric characters and ending with 3 alphanumeric characters.")
	private String gstNo;
	
	@NotBlank(message = "License No. cannot be blank")
	@NotNull(message = "License No. cannot be null")
	@Pattern(regexp = "^[A-Z0-9]{8,15}$",message = "Invalid license number. It should be between 8 and 15 characters,"
			+ " consisting of uppercase letters and numbers only.")
	private String licenseNo;
	
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	
	 
}
