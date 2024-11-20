package com.pharmassist.entity;

import com.pharmassist.config.GenerateCustomId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BillItem {
	@Id
	@GenerateCustomId
	private String billItemId;

	private Integer quantity;
	private Double pricePerItem;
	private Double totalPrice;

	@ManyToOne
	@JoinColumn(name = "bag_id", nullable = false)
	private Bag bag;

	@ManyToOne
	@JoinColumn(name = "medicine_id", nullable = false)
	private Medicine medicine;

	public String getBillItemId() {
		return billItemId;
	}

	public void setBillItemId(String billItemId) {
		this.billItemId = billItemId;
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

	public Bag getBag() {
		return bag;
	}

	public void setBag(Bag bag) {
		this.bag = bag;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
	
	
}
