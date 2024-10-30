package com.pharmassist.controller;

import org.springframework.stereotype.Controller;

import com.pharmassist.service.PharmacyService;
import com.pharmassist.util.AppResponseBuilder;


@Controller
public class PharmacyController {
	
	private final PharmacyService pharmacyService;
	private final AppResponseBuilder response;
	
	public PharmacyController(PharmacyService pharmacyService, AppResponseBuilder response) {
		this.pharmacyService = pharmacyService;
		this.response = response;
	}
	
	
	
}
