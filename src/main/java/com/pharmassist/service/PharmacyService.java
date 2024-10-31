package com.pharmassist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Admin;
import com.pharmassist.entity.Pharmacy;
import com.pharmassist.exception.AdminNotFoundByIdException;
import com.pharmassist.exception.PharmacyNotFoundByIdException;
import com.pharmassist.mapper.AdminMapper;
import com.pharmassist.mapper.PharmacyMapper;
import com.pharmassist.repository.AdminRepository;
import com.pharmassist.repository.PharmacyRepository;
import com.pharmassist.requestdto.PharmacyRequest;
import com.pharmassist.responsedto.PharmacyResponse;

@Service
public class PharmacyService {
	
	private final PharmacyRepository pharmacyRepository;
	private final AdminRepository adminRepository;
	private final PharmacyMapper pharmacyMapper;
	
	public PharmacyService(PharmacyRepository pharmacyRepository, PharmacyMapper pharmacyMapper,AdminRepository adminRepository) {
		super();
		this.pharmacyRepository = pharmacyRepository;
		this.pharmacyMapper = pharmacyMapper;
		this.adminRepository=adminRepository;
	}

	
	public PharmacyResponse savePharmacy(PharmacyRequest pharmacyRequest,String adminId) {	
		 return adminRepository.findById(adminId)
						.map((admin)-> {
							Pharmacy pharmacy = pharmacyRepository.save(pharmacyMapper.mapToPhamacy(pharmacyRequest, new Pharmacy()));
							admin.setPharmacy(pharmacy);
							adminRepository.save(admin);
							return pharmacy;
						})
						.map(pharmacyMapper::mapToPharmacyResponse)
						.orElseThrow(()-> new AdminNotFoundByIdException("Failed to add Pharmacy due to no Admin found"));
	}


	public List<PharmacyResponse> findAllPharmacy() {
		return pharmacyRepository.findAll()
							.stream()
							.map(pharmacyMapper::mapToPharmacyResponse)
							.toList();
	}


	public PharmacyResponse findPharmacy(String adminId) {
		return adminRepository.findById(adminId)
								.map((admin)->{
									Pharmacy pharmacy = admin.getPharmacy();
									return pharmacy!=null 
											? pharmacyMapper.mapToPharmacyResponse(pharmacy)
											: throwPharmacyNotFound();
								})
								.orElseThrow(()-> new AdminNotFoundByIdException("Failed to find Pharmacy due to no admin found"));
	}
	
	public static PharmacyResponse throwPharmacyNotFound() {
		throw new PharmacyNotFoundByIdException("Failed to find Pharmacy due to no pharmacy associated with respective Admin");
	}
	
	
	
}
