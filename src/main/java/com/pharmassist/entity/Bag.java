package com.pharmassist.entity;

import java.util.List;

import com.pharmassist.config.GenerateCustomId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Bag {

	@Id
	@GenerateCustomId
	private String bagId;

	@ManyToOne(optional = false) // Explicitly indicate non-nullability
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    @OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> billItems;

    @OneToOne(mappedBy = "bag", cascade = CascadeType.ALL)
    private Bill bill;

	public String getBagId() {
		return bagId;
	}

	public void setBagId(String bagId) {
		this.bagId = bagId;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public List<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<BillItem> billItems) {
		this.billItems = billItems;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}



}