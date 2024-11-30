package com.pharmassist.util;

public class UpdateQuantityRequest{
	private String billItemId;
	private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

	public String getBillItemId() {
		return billItemId;
	}

	public void setBillItemId(String billItemId) {
		this.billItemId = billItemId;
	}
    
    
}

