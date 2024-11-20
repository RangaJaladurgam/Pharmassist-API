package com.pharmassist.entity;

import java.time.LocalDateTime;

import com.pharmassist.config.GenerateCustomId;
import com.pharmassist.enums.PaymentMode;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Bill {
	@Id
	@GenerateCustomId
	private String billId;
	private double gstInPercentage;
	private double totalAmount;
	private double totalPayableAmount;
	private LocalDateTime dateTime;
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	@OneToOne(optional = false)
	@JoinColumn(name = "bag_id", nullable = false)
	private Bag bag;

	@ManyToOne(optional = false)
	private Patient patient;

	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public double getGstInPercentage() {
		return gstInPercentage;
	}
	public void setGstInPercentage(double gstInPercentage) {
		this.gstInPercentage = gstInPercentage;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getTotalPayableAmount() {
		return totalPayableAmount;
	}
	public void setTotalPayableAmount(double totalPayableAmount) {
		this.totalPayableAmount = totalPayableAmount;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}




}