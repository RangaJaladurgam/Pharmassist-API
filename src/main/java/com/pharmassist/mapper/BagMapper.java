package com.pharmassist.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pharmassist.entity.Bag;
import com.pharmassist.responsedto.BagResponse;
import com.pharmassist.responsedto.BillItemResponse;

@Component
public class BagMapper {
	
	private final BillItemMapper billItemMapper;
	
	public BagMapper(BillItemMapper billItemMapper) {
		super();
		this.billItemMapper = billItemMapper;
	}
	
	public BagResponse mapToBagResponse(Bag bag) {
		BagResponse response = new BagResponse();
		response.setBagId(bag.getBagId());
		
		 List<BillItemResponse> billItemResponses = new ArrayList<>();
	        bag.getBillItems().forEach(billItem -> 
	            billItemResponses.add(billItemMapper.mapToBillItemResponse(billItem))
	        );
	        
	        response.setBillItems(billItemResponses);
		return response;
	}
}
