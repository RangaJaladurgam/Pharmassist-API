package com.pharmassist.mapper;

import org.springframework.stereotype.Component;

import com.pharmassist.entity.Admin;
import com.pharmassist.requestdto.AdminRequest;
import com.pharmassist.responsedto.AdminResponse;

@Component
public class AdminMapper {
		
	private PharmacyMapper mapper;	
	
	public AdminMapper(PharmacyMapper mapper) {
		super();
		this.mapper = mapper;
	}

	public Admin mapToAdmin(AdminRequest adminRequest,Admin admin) {
		admin.setEmail(adminRequest.getEmail());
		admin.setPassword(adminRequest.getPassword());
		admin.setPhoneNumber(adminRequest.getPhoneNumber());
		return admin;
	}
	
	public AdminResponse mapToAdminResponse(Admin admin) {
		AdminResponse adminResponse = new AdminResponse();
		adminResponse.setAdminId(admin.getAdminId());
		adminResponse.setEmail(admin.getEmail());
		adminResponse.setPhoneNumber(admin.getPhoneNumber());
		if(admin.getPharmacy()==null)
			adminResponse.setPharmacyResponse(null);
		else
			adminResponse.setPharmacyResponse(mapper.mapToPharmacyResponse(admin.getPharmacy()));
		return adminResponse;
	}
}
