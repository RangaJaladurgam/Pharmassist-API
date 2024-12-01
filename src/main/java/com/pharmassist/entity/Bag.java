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
	
	@OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> billItems;

	@ManyToOne(optional = false) // Explicitly indicate non-nullability
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

	@OneToOne(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Bill bill;
    
    
    public void addItem(BillItem billItem) {
    	billItems.add(billItem);
    	billItem.setBag(this);
    }
    
    public void removeItem(BillItem billItem) {
    	billItems.remove(billItem);
    	billItem.setBag(null);
    }
    
    public void clearItems() {
    	billItems.clear();
    }

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
