package com.pharmassist.service;

import org.springframework.stereotype.Service;

import com.pharmassist.mapper.PatientMapper;
import com.pharmassist.repository.PatientRepository;

@Service
public class PatientService {
	
	private final PatientRepository patientRepository;
	private final PatientMapper patientMapper;
	
	public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
		super();
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
	}
	
	
}
