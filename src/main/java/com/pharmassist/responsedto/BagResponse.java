package com.pharmassist.responsedto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pharmassist.entity.BillItem;

public class BagResponse {
	
	private String bagId;
	private List<BillItemResponse> billItems;
	
	public String getBagId() {
		return bagId;
	}
	public void setBagId(String bagId) {
		this.bagId = bagId;
	}
	public List<BillItemResponse> getBillItems() {
		return billItems;
	}
	public void setBillItems(List<BillItemResponse> billItems) {
		this.billItems = billItems;
	}
	
	
	
}
