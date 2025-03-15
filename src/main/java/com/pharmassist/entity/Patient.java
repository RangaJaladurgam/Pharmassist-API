package com.pharmassist.entity;

import java.time.LocalDate;
import java.util.List;

import com.pharmassist.config.GenerateCustomId;
import com.pharmassist.enums.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Patient {
	
	@Id
	@GenerateCustomId
	private String patientId;
	private String name;
	private String phoneNumber;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	@OneToMany
	private List<Bill> bills;
	
	
	public List<Bill> getBills() {
		return bills;
	}
	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
	public Pharmacy getPharmacy() {
		return pharmacy;
	}
	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
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
