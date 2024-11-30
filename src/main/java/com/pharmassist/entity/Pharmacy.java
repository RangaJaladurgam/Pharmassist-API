package com.pharmassist.entity;

import java.util.List;

import com.pharmassist.config.GenerateCustomId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Pharmacy {

	@Id
	@GenerateCustomId
	private String pharmacyId;
	private String name;
	private String gstNo;
	private String licenseNo;

	@OneToOne(mappedBy = "pharmacy")
	private Admin admin;

	@OneToMany(mappedBy = "pharmacy")
	private List<Patient> patients;

	@OneToMany(mappedBy = "pharmacy")
	private List<Medicine> medicines;

	@OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
	private List<Bag> bags;


	public List<Medicine> getMedicines() {
		return medicines;
	}
	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	public List<Bag> getBags() {
		return bags;
	}
	public void setBags(List<Bag> bags) {
		this.bags = bags;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

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
	public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}



}
