package com.pharmassist.entity;

import com.pharmassist.config.GenerateCustomId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pharmacy {
	
	@Id
	@GenerateCustomId
	private String pharmacyId;
	private String name;
	private String gstNo;
	private String licenseNo;
	
	public String getPharmacyId() {
		return pharmacyId;
	}
	public void setPharmacyId(String pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGstNo() {
		return gstNo;
	}
	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	
	
	
}
