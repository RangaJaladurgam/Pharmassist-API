package com.pharmassist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Pharmacy;
import com.pharmassist.exception.AdminNotFoundByIdException;
import com.pharmassist.exception.NoPharmaciesFoundException;
import com.pharmassist.exception.PharmacyNotFoundByIdException;
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

	
	public PharmacyResponse savePharmacy(PharmacyRequest pharmacyRequest,String email) {	
		 return adminRepository.findByEmail(email)
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
		List<Pharmacy> pharmacies = pharmacyRepository.findAll();
		if(pharmacies.isEmpty())
			throw new NoPharmaciesFoundException("Failed to Find All Pharmacies");
		return pharmacies.stream()
						.map(pharmacyMapper::mapToPharmacyResponse)
						.toList();
	}


	public PharmacyResponse findPharmacy(String adminId) {
		return adminRepository.findPharmacyByAdminId(adminId)
						.map(pharmacyMapper::mapToPharmacyResponse)
						.orElseThrow(()-> new PharmacyNotFoundByIdException("Failed to find Pharmacy"));
	}


	public PharmacyResponse updatePharmacy(PharmacyRequest pharmacyRequest, String email) {
		return adminRepository.findByEmail(email)
							.map((admin)->{
								Pharmacy pharmacy = admin.getPharmacy();
								pharmacy = pharmacyRepository.save(pharmacyMapper.mapToPhamacy(pharmacyRequest, pharmacy));
								return pharmacyMapper.mapToPharmacyResponse(pharmacy);
							})
							.orElseThrow(() -> new PharmacyNotFoundByIdException("Failed to Update the Pharmacy"));
	}
	
	
	
}
