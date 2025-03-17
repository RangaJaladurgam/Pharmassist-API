package com.pharmassist.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Admin;
import com.pharmassist.entity.Patient;
import com.pharmassist.exception.AdminNotFoundByIdException;
import com.pharmassist.exception.NoPatientsFoundException;
import com.pharmassist.exception.PatientNotFoundByIdException;
import com.pharmassist.exception.PharmacyNotFoundByIdException;
import com.pharmassist.mapper.PatientMapper;
import com.pharmassist.repository.AdminRepository;
import com.pharmassist.repository.PatientRepository;
import com.pharmassist.repository.PharmacyRepository;
import com.pharmassist.requestdto.PatientRequest;
import com.pharmassist.responsedto.PatientResponse;

@Service
public class PatientService {
	
	private final PatientRepository patientRepository;
	private final PharmacyRepository pharmacyRepository;
	private final PatientMapper patientMapper;
	private final AdminRepository adminRepository;
	
	public PatientService(PatientRepository patientRepository,PharmacyRepository pharmacyRepository,PatientMapper patientMapper, AdminRepository adminRepository) {
		super();
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
		this.pharmacyRepository = pharmacyRepository;
		this.adminRepository = adminRepository;
	}

	public PatientResponse addPatient(PatientRequest patientRequest, String email) {
		Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundByIdException("Admin not found"));
		if(admin.getPharmacy()==null)
			throw new PharmacyNotFoundByIdException("Pharmacy is not Linked to Admin");
	    String pharmacyId = admin.getPharmacy()
	                                       .getPharmacyId();
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

	
	public List<PatientResponse> findAllPatientsByPharmacy(String email) {
		Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundByIdException("Admin not found"));
		if(admin.getPharmacy()==null)
			throw new PharmacyNotFoundByIdException("Pharmacy is not Linked to Admin");
	    String pharmacyId = admin.getPharmacy()
	                                       .getPharmacyId();
		List<Patient> patients = patientRepository.findPatientsByPharmacy(pharmacyId);
		if(patients.isEmpty())
			throw new NoPatientsFoundException("Failed to find all pharmacy");
		return patients.stream()
						.map(patientMapper::mapToPatientResponse)
						.toList();
						
	}

	public PatientResponse updatePatient(PatientRequest patientRequest, String patientId) {
		return patientRepository.findById(patientId)
					.map((patient)->{
						patient = patientRepository.save(patientMapper.mapToPatient(patientRequest, patient));
						return patientMapper.mapToPatientResponse(patient);
					})
					.orElseThrow(()-> new PatientNotFoundByIdException("Failed to update Patient By Id"));
	}

	public PatientResponse findPatientByPhoneNumber(String email,String phoneNumber) {
		Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new AdminNotFoundByIdException("Admin not found"));
		if(admin.getPharmacy()==null)
			throw new PharmacyNotFoundByIdException("Pharmacy is not Linked to Admin");
	    String pharmacyId = admin.getPharmacy()
	                                       .getPharmacyId();
		return patientRepository.findByPhoneNumber(pharmacyId,phoneNumber)
							.map(patientMapper::mapToPatientResponse)
							.orElseThrow(()-> new PatientNotFoundByIdException("Failed to find Patient by PhoneNumber"));
	}
	
	
}
