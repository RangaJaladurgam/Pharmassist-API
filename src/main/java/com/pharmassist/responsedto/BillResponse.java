package com.pharmassist.responsedto;

import java.time.LocalDateTime;

import com.pharmassist.entity.Bag;
import com.pharmassist.entity.Patient;
import com.pharmassist.enums.PaymentMode;

public class BillResponse {
	
	private String billId;
	private double gstInPercentage;
	private double totalAmount;
	private double totalPayableAmount;
	private LocalDateTime dateTime;
	private PaymentMode paymentMode;
	private PatientResponse patientResponse;
	private BagResponse bagResponse;
	

	
	
	public PatientResponse getPatientResponse() {
		return patientResponse;
	}
	public void setPatientResponse(PatientResponse patientResponse) {
		this.patientResponse = patientResponse;
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
	public BagResponse getBagResponse() {
		return bagResponse;
	}
	public void setBagResponse(BagResponse bagResponse) {
		this.bagResponse = bagResponse;
	}
	
	
	
}
