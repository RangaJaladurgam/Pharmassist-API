package com.pharmassist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pharmassist.requestdto.PharmacyRequest;
import com.pharmassist.responsedto.PharmacyResponse;
import com.pharmassist.service.PharmacyService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;

import jakarta.validation.Valid;


@RestController
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
	
	@GetMapping("/pharmacy")
	public ResponseEntity<ResponseStructure<List<PharmacyResponse>>> findAllPharmacy(){
		List<PharmacyResponse> pharmacyResponses = pharmacyService.findAllPharmacy();
		return response.success(HttpStatus.FOUND, "Pharmacies Found", pharmacyResponses);
	}
	
}
