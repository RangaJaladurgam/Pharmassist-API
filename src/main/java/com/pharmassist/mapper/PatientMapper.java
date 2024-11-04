package com.pharmassist.mapper;

import org.springframework.stereotype.Component;

import com.pharmassist.entity.Patient;
import com.pharmassist.requestdto.PatientRequest;
import com.pharmassist.responsedto.PatientResponse;

@Component
public class PatientMapper {

	
	public Patient mapToPaient(PatientRequest patientRequest,Patient patient) {
		patient.setName(patientRequest.getName());
		patient.setPhoneNumber(patientRequest.getPhoneNumber());
		patient.setEmail(patientRequest.getEmail());
		patient.setGender(patientRequest.getGender());
		patient.setDateOfBirth(patientRequest.getDateOfBirth());
		
		return patient;
	}
	
	public PatientResponse mapPatientResponse(Patient patient) {
		PatientResponse patientResponse = new PatientResponse();
		patientResponse.setName(patient.getName());
		patientResponse.setPatientId(patient.getPatientId());
		patientResponse.setGender(patient.getGender());
		patientResponse.setEmail(patient.getEmail());
		
		return patientResponse;
	}
	
}	
