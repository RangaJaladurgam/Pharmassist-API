package com.pharmassist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pharmassist.service.MedicineService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.SimpleResponseStructure;

@RestController
public class MedicineController {
	
	private final MedicineService medicineService;
	private final AppResponseBuilder response;
	
	public MedicineController(MedicineService medicineService, AppResponseBuilder response) {
		super();
		this.medicineService = medicineService;
		this.response = response;
	}
	
	@PostMapping("/pharmacy/{pharmacyId}/medicines/upload")
	public ResponseEntity<SimpleResponseStructure> uploadMedicines(@RequestParam MultipartFile file,@PathVariable String pharmacyId){
		String message = medicineService.uploadMedicines(file,pharmacyId);
		return response.success(HttpStatus.CREATED, message);
	}
	
}
