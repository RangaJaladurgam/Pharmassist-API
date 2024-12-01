package com.pharmassist.responsedto;

import org.springframework.stereotype.Component;

public class BillItemResponse {
	
	private String billItemId;
	private String name;
	private String dosage;
	private Integer quantity;
	private Double pricePerItem;
	private Double totalPrice;
	
	public String getBillItemId() {
		return billItemId;
	}
	public void setBillItemId(String billItemId) {
		this.billItemId = billItemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPricePerItem() {
		return pricePerItem;
	}
	public void setPricePerItem(Double pricePerItem) {
		this.pricePerItem = pricePerItem;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
