package com.pharmassist.controller;

import org.springframework.web.bind.annotation.RestController;

import com.pharmassist.service.PatientService;
import com.pharmassist.util.AppResponseBuilder;

@RestController
public class PatientController {

	private final PatientService patientService;
	private final AppResponseBuilder response;
	
	public PatientController(PatientService patientService, AppResponseBuilder response) {
		super();
		this.patientService = patientService;
		this.response = response;
	}
	
	
}
