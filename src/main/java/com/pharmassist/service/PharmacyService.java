package com.pharmassist.service;

import org.springframework.stereotype.Service;

import com.pharmassist.mapper.PharmacyMapper;
import com.pharmassist.repository.PharmacyRepository;

@Service
public class PharmacyService {
	
	private final PharmacyRepository pharmacyRepository;
	private final PharmacyMapper pharmacyMapper;
	
	public PharmacyService(PharmacyRepository pharmacyRepository, PharmacyMapper pharmacyMapper) {
		super();
		this.pharmacyRepository = pharmacyRepository;
		this.pharmacyMapper = pharmacyMapper;
	}
	
	
	
}
