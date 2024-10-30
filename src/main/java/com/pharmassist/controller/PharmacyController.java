package com.pharmassist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pharmassist.requestdto.PharmacyRequest;
import com.pharmassist.responsedto.PharmacyResponse;
import com.pharmassist.service.PharmacyService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;

import jakarta.validation.Valid;


@Controller
public class PharmacyController {
	
	private final PharmacyService pharmacyService;
	private final AppResponseBuilder response;
	
	public PharmacyController(PharmacyService pharmacyService, AppResponseBuilder response) {
		this.pharmacyService = pharmacyService;
		this.response = response;
	}
	
	@PostMapping("/pharmacy/{adminId}")
	public ResponseEntity<ResponseStructure<PharmacyResponse>> savePharmacy(@RequestBody @Valid PharmacyRequest pharmacyRequest,@PathVariable String adminId){
		PharmacyResponse pharmacyResponse = pharmacyService.savePharmacy(pharmacyRequest,adminId);
		return response.success(HttpStatus.CREATED, "Pharmacy Added", pharmacyResponse);
	}
	
}
