package com.pharmassist.mapper;

import org.springframework.stereotype.Component;

import com.pharmassist.entity.Pharmacy;
import com.pharmassist.requestdto.PharmacyRequest;
import com.pharmassist.responsedto.PharmacyResponse;

@Component
public class PharmacyMapper {
	
	public Pharmacy mapToPhamacy(PharmacyRequest pharmacyRequest,Pharmacy pharmacy) {
		pharmacy.setName(pharmacyRequest.getName());
		pharmacy.setGstNo(pharmacyRequest.getGstNo());
		pharmacy.setLicenseNo(pharmacyRequest.getLicenseNo());
		return pharmacy;
	}
	
	public PharmacyResponse mapToPharmacyResponse(Pharmacy pharmacy) {
		PharmacyResponse pharmacyResponse = new PharmacyResponse();
		pharmacyResponse.setName(pharmacy.getName());
		pharmacyResponse.setLicenseNo(pharmacy.getLicenseNo());
		pharmacyResponse.setPharmacyId(pharmacy.getPharmacyId());
		pharmacyResponse.setGstNo(pharmacy.getGstNo());
		return pharmacyResponse;
	}
}
