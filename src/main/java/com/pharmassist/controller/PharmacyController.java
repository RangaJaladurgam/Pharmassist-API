package com.pharmassist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pharmassist.requestdto.PharmacyRequest;
import com.pharmassist.responsedto.PharmacyResponse;
import com.pharmassist.security.UserDetailsImpl;
import com.pharmassist.service.PharmacyService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ErrorStructure;
import com.pharmassist.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@Tag(name="Pharmacy Controller",description = "The controller provides end-points to operate on Pharmacy Entity")
public class PharmacyController {
	
	private final PharmacyService pharmacyService;
	private final AppResponseBuilder response;
	
	public PharmacyController(PharmacyService pharmacyService, AppResponseBuilder response) {
		this.pharmacyService = pharmacyService;
		this.response = response;
	}
	
	@Operation(description = "The End-point can be used to save the Admin",
			responses = {
					@ApiResponse(responseCode = "201",description = "Pharmacy Added",
					content = {
							@Content (schema = @Schema(implementation = PharmacyResponse.class))
					}),
					@ApiResponse(responseCode = "400",description = "Invalid Input",
					content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class))
					}),
					@ApiResponse(responseCode = "404",description = "Admin Not Found",
					content = {
							@Content(schema = @Schema(implementation = ErrorStructure.class))
					})
			})
	@PostMapping("/admins/pharmacy")
	public ResponseEntity<ResponseStructure<PharmacyResponse>> savePharmacy(@RequestBody @Valid PharmacyRequest pharmacyRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName(); 
		PharmacyResponse pharmacyResponse = pharmacyService.savePharmacy(pharmacyRequest,email);
		return response.success(HttpStatus.CREATED, "Pharmacy Added", pharmacyResponse);
	}
	
	@Operation(description = "The End-point can be used to list all pharmacies",
			responses = {
					@ApiResponse(responseCode = "302",description = "Pharmacies Found",
							content = {
									@Content(schema = @Schema(implementation = PharmacyResponse.class))
							}),
					@ApiResponse(responseCode = "404",description = "No Pharmcies Found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							})
			})
	@GetMapping("/pharmacy")
	public ResponseEntity<ResponseStructure<List<PharmacyResponse>>> findAllPharmacy(){
		List<PharmacyResponse> pharmacyResponses = pharmacyService.findAllPharmacy();
		return response.success(HttpStatus.FOUND, "Pharmacies Found", pharmacyResponses);
	}
	
	@Operation(description = "The End-point can be used to find the Pharmacy Based on the Admin ID",
			responses = {
					@ApiResponse(responseCode = "302",description = "Pharmacy Found",
							content = {
									@Content(schema = @Schema(implementation = PharmacyResponse.class))
							}),
					@ApiResponse(responseCode = "404",description = "Pharmacy Not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							})
			})
	@GetMapping("/admins/{adminId}/pharmacy")
	public ResponseEntity<ResponseStructure<PharmacyResponse>> findPharmacy(@PathVariable String adminId){
		PharmacyResponse pharmacyResponse = pharmacyService.findPharmacy(adminId);
		return response.success(HttpStatus.FOUND, "Pharmacy Found", pharmacyResponse);
	}
	
	@Operation(description = "The End-point can be used to update the Pharmacy",
			responses = {
					@ApiResponse(responseCode = "200",description = "Pharmacy Updated",
							content = {
									@Content(schema = @Schema(implementation = PharmacyResponse.class))
							}),
					@ApiResponse(responseCode = "400",description = "Bad PharmacyRequest, Invalid Input",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}),
					@ApiResponse(responseCode = "404",description = "Pharmacy Not Found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							})
			})
	@PutMapping("/pharmacy/{pharmacyId}")
	public ResponseEntity<ResponseStructure<PharmacyResponse>> updatePharmacy(@RequestBody @Valid PharmacyRequest pharmacyRequest,@PathVariable String pharmacyId){
		PharmacyResponse pharmacyResponse = pharmacyService.updatePharmacy(pharmacyRequest,pharmacyId);
		return response.success(HttpStatus.OK, "Updated Pharmacy", pharmacyResponse);
	}
	
}
