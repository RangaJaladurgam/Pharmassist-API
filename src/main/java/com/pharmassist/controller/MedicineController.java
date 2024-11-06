package com.pharmassist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pharmassist.responsedto.MedicineResponse;
import com.pharmassist.service.MedicineService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;
import com.pharmassist.util.SimpleResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "MedicineController",description = "The End-points can be used to operate on Medicine")
public class MedicineController {
	
	private final MedicineService medicineService;
	private final AppResponseBuilder response;
	
	public MedicineController(MedicineService medicineService, AppResponseBuilder response) {
		super();
		this.medicineService = medicineService;
		this.response = response;
	}
	
	@Operation(description = "The End-point can be used to upload medicines Excel file",
			responses = {
			@ApiResponse(responseCode = "201",description = "Medicines Uploaded",
			content = {
					@Content(schema = @Schema(implementation = SimpleResponseStructure.class))
					})
	})
	@PostMapping("/pharmacy/{pharmacyId}/medicines/upload")
	public ResponseEntity<SimpleResponseStructure> uploadMedicines(@RequestParam MultipartFile file,@PathVariable String pharmacyId){
		String message = medicineService.uploadMedicines(file,pharmacyId);
		return response.success(HttpStatus.CREATED, message);
	}
	
	@Operation(description = "The End-point can be used to find medicines by Name or Ingredients",
			responses = {
					@ApiResponse(responseCode = "302",description = "Medicines Found",
							content = {
									@Content(schema = @Schema(implementation = MedicineResponse.class))
							})
			})
	@GetMapping("/medicines/{userInput}")
	public ResponseEntity<ResponseStructure<List<MedicineResponse>>> findMedicineByNameOrIngredients(@PathVariable String userInput){
		List<MedicineResponse> medicines = medicineService.findMedicineByNameOrIngredients(userInput);
		return response.success(HttpStatus.FOUND, "Medicines Found", medicines);
	}
	
}
