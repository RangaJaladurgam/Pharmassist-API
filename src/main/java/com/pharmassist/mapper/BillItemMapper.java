package com.pharmassist.mapper;

import org.springframework.stereotype.Component;

import com.pharmassist.entity.BillItem;
import com.pharmassist.responsedto.BillItemResponse;

@Component
public class BillItemMapper {
	
	public BillItemResponse mapToBillItemResponse(BillItem billItem) {
		BillItemResponse response = new BillItemResponse();
		response.setBillItemId(billItem.getBillItemId());
		response.setDosage(billItem.getDosage());
		response.setName(billItem.getName());
		response.setPricePerItem(billItem.getPricePerItem());
		response.setQuantity(billItem.getQuantity());
		response.setTotalPrice(billItem.getTotalPrice());
		return response;
	}
}
