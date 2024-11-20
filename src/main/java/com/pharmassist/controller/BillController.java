package com.pharmassist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmassist.entity.Bill;
import com.pharmassist.entity.Patient;
import com.pharmassist.enums.PaymentMode;
import com.pharmassist.service.BillService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;

@RestController
public class BillController {
	
	private final BillService billService;
	private final AppResponseBuilder response;
	
	public BillController(BillService billService, AppResponseBuilder response) {
		super();
		this.billService = billService;
		this.response = response;
	}
	
	@PostMapping("/bills/generatebill")
	public ResponseEntity<ResponseStructure<Bill>> generateBill(double gstInPercentage,PaymentMode paymentMode,Patient patient){
		String email = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();											
		Bill bill = billService.generateBill(email, gstInPercentage, paymentMode, patient);
		return response.success(HttpStatus.CREATED, "Bill Generated..", bill);
	}
	
	
}
