package com.pharmassist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Admin;
import com.pharmassist.exception.AdminNotFoundByIdException;
import com.pharmassist.exception.NoAdminsFoundException;
import com.pharmassist.mapper.AdminMapper;
import com.pharmassist.repository.AdminRepository;
import com.pharmassist.requestdto.AdminRequest;
import com.pharmassist.responsedto.AdminResponse;

@Service
public class AdminService {
	
	private final AdminRepository adminRepository;
	private final AdminMapper adminMapper;

	public AdminService(AdminRepository adminRepository,AdminMapper adminMapper) {
		this.adminRepository = adminRepository;
		this.adminMapper = adminMapper;
	}

	public AdminResponse saveUser(AdminRequest adminRequest) {
		Admin admin = adminRepository.save(adminMapper.mapToAdmin(adminRequest, new Admin()));
		return adminMapper.mapToAdminResponse(admin);
	}

	public AdminResponse findAdmin(String adminId) {
		return adminRepository.findById(adminId)
						.map((admin)-> adminMapper.mapToAdminResponse(admin))
						.orElseThrow(()-> new AdminNotFoundByIdException("Failed to find the Admin"));
	}

	public AdminResponse updateAdmin(String adminId, AdminRequest adminRequest) {
		return adminRepository.findById(adminId)
						.map((exAdmin)->{
							adminMapper.mapToAdmin(adminRequest, exAdmin);
							return adminRepository.save(exAdmin);
						})
						.map(adminMapper::mapToAdminResponse)
						.orElseThrow(()-> new AdminNotFoundByIdException("Failed to Update the Admin"));
	}

	public List<AdminResponse> findAllAdmins() {
		List<Admin> admins = adminRepository.findAll();
		if(admins.isEmpty())
			throw new NoAdminsFoundException("Failed to find all Admins");
		return admins.stream()
					.map(adminMapper::mapToAdminResponse)
					.toList();
	}
}
