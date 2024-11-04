package com.pharmassist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Patient;
import com.pharmassist.exception.NoPatientsFoundException;
import com.pharmassist.exception.PharmacyNotFoundByIdException;
import com.pharmassist.mapper.PatientMapper;
import com.pharmassist.repository.PatientRepository;
import com.pharmassist.repository.PharmacyRepository;
import com.pharmassist.requestdto.PatientRequest;
import com.pharmassist.responsedto.PatientResponse;

@Service
public class PatientService {
	
	private final PatientRepository patientRepository;
	private final PharmacyRepository pharmacyRepository;
	private final PatientMapper patientMapper;
	
	public PatientService(PatientRepository patientRepository,PharmacyRepository pharmacyRepository,PatientMapper patientMapper) {
		super();
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
		this.pharmacyRepository = pharmacyRepository;
	}

	public PatientResponse addPatient(PatientRequest patientRequest, String pharmacyId) {
		
		return pharmacyRepository.findById(pharmacyId)
						.map((pharmacy)-> {
							Patient patient = patientMapper.mapToPatient(patientRequest, new Patient());
							patient.setPharmacy(pharmacy);
							
							if(pharmacy.getPatients()==null)
								pharmacy.setPatients(new ArrayList<>());
							
							pharmacy.getPatients().add(patient);
		 
							patient = patientRepository.save(patient);
							return patientMapper.mapToPatientResponse(patient);
						})
						.orElseThrow(()-> new PharmacyNotFoundByIdException("Failed to Add Patient due to No Pharmacy "
								+ "Found with id : "+pharmacyId));
		
	}

	
	public List<PatientResponse> findAllPatientsByPharmacy(String pharmacyId) {
		
		List<Patient> patients = patientRepository.findPatientsByPharmacy(pharmacyId);
		if(patients.isEmpty())
			throw new NoPatientsFoundException("Failed to find all pharmacy");
		return patients.stream()
						.map(patientMapper::mapToPatientResponse)
						.toList();
						
	}
	
	
}
