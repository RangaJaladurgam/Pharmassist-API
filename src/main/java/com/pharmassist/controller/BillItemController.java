package com.pharmassist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmassist.entity.BillItem;
import com.pharmassist.service.BillItemService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;

@RestController
public class BillItemController {
	
	private final BillItemService billItemService;
	private final AppResponseBuilder response;

	public BillItemController(BillItemService billItemService, AppResponseBuilder response) {
		super();
		this.billItemService = billItemService;
		this.response = response;
	}
	
	@PostMapping("/billItems/medicines/{medicineId}/Bags/{bagId}")
	public ResponseEntity<ResponseStructure<BillItem>> addBillItem(@PathVariable String bagId,@PathVariable String medicineId,@RequestParam int quantity) {
		BillItem billItem = billItemService.addBillItem(bagId,medicineId,quantity);
		return response.success(HttpStatus.CREATED, "Medicine Added to BillItem", billItem);
	}
}
