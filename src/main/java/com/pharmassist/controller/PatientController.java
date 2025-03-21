package com.pharmassist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
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
	@PostMapping("/pharmacy/patients")
	private ResponseEntity<ResponseStructure<PatientResponse>> addPatient(@RequestBody @Valid PatientRequest patientRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		PatientResponse patientResponse = patientService.addPatient(patientRequest,email);
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
	@GetMapping("/pharmacy/patients")
	private ResponseEntity<ResponseStructure<List<PatientResponse>>> findAllPatientsByPharmacy(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		List<PatientResponse> patientsResponses = patientService.findAllPatientsByPharmacy(email);
		return response.success(HttpStatus.FOUND, "Patients Found", patientsResponses);
	}
	
	@Operation(description = "The End-point can be used to update Patient by Patient ID",
			responses = {
					@ApiResponse(responseCode = "200",description = "Patient Updated",
							content = {
									@Content(schema = @Schema(implementation = PatientResponse.class))
							}),
					@ApiResponse(responseCode = "400",description = "Bad Patient Request",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}),
					@ApiResponse(responseCode = "404",description = "Patient Not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							})
			})
	@PutMapping("/patients/{patientId}")
	private ResponseEntity<ResponseStructure<PatientResponse>> updatePatient(@RequestBody @Valid PatientRequest patientRequest,@PathVariable String patientId){
		PatientResponse patientResponse = patientService.updatePatient(patientRequest,patientId);
		return response.success(HttpStatus.OK, "Patient Updated", patientResponse);
	}
	
	
	@GetMapping("/patients/{phoneNumber}")
	private ResponseEntity<ResponseStructure<PatientResponse>> findPatientByPhoneNumber(@PathVariable String phoneNumber){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		PatientResponse patientResponse = patientService.findPatientByPhoneNumber(email,phoneNumber);
		return response.success(HttpStatus.FOUND, "Patient Found", patientResponse);
	}
	
	
}
