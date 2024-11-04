package com.pharmassist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pharmassist.entity.Patient;
import com.pharmassist.requestdto.PatientRequest;
import com.pharmassist.responsedto.PatientResponse;
import com.pharmassist.service.PatientService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ErrorStructure;
import com.pharmassist.util.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Patient Controller",description = "The End-points can be used to operate on Patient Entity")
public class PatientController {

	private final PatientService patientService;
	private final AppResponseBuilder response;
	
	public PatientController(PatientService patientService, AppResponseBuilder response) {
		super();
		this.patientService = patientService;
		this.response = response;
	}
	
	@Operation(description = "The End-point can be used to Add Patient",
			responses = {
					@ApiResponse(responseCode = "201",description = "Patient Added",
							content = {
									@Content(schema = @Schema(implementation = PatientResponse.class))
							}),
					@ApiResponse(responseCode = "400",description = "Bad Patient Request, Invalid Inputs",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}),
					@ApiResponse(responseCode = "404",description = "Pharmact Not Found By Id",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							})
			})
	@PostMapping("/pharmacy/{pharmacyId}/patients")
	private ResponseEntity<ResponseStructure<PatientResponse>> addPatient(@RequestBody PatientRequest patientRequest,@PathVariable String pharmacyId){
		PatientResponse patientResponse = patientService.addPatient(patientRequest,pharmacyId);
		return response.success(HttpStatus.CREATED,"Patient Added", patientResponse);
	}
	
	@Operation(description = "The End-point can be used to Find all patients by Pharmacy Id",
			responses = {
					@ApiResponse(responseCode = "302",description = "Patients Found",
							content = {
									@Content(schema = @Schema(implementation = ResponseStructure.class))
							}),
					@ApiResponse(responseCode = "404",description = "No Patients Found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							})
			})
	@GetMapping("pharmacy/{pharmacyId}/patients")
	private ResponseEntity<ResponseStructure<List<PatientResponse>>> findAllPatientsByPharmacy(@PathVariable String pharmacyId){
		List<PatientResponse> patientsResponses = patientService.findAllPatientsByPharmacy(pharmacyId);
		return response.success(HttpStatus.FOUND, "Patients Found", patientsResponses);
	}
	
	
}
