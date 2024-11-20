package com.pharmassist.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Bill;
import com.pharmassist.entity.Patient;
import com.pharmassist.enums.PaymentMode;
import com.pharmassist.repository.AdminRepository;
import com.pharmassist.repository.BillRepository;

@Service
public class BillService {
	
	private final BillRepository billRepository;
	private final AdminRepository adminRepository;
	private Bill bill;
	
	public BillService(BillRepository billRepository, AdminRepository adminRepository) {
		super();
		this.billRepository = billRepository;
		this.adminRepository = adminRepository;
	}

	public Bill generateBill(String email,double gstInPercentage,PaymentMode paymentMode,Patient patient) {
		return null;
	}
	
	
	
	
}
