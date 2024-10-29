package com.pharmassist.service;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Admin;
import com.pharmassist.exception.AdminNotFoundByIdException;
import com.pharmassist.mapper.AdminMapper;
import com.pharmassist.repository.AdminRepository;
import com.pharmassist.requestdto.AdminRequest;
import com.pharmassist.responsedto.AdminResponse;

@Service
public class AdminService {
	
	private AdminRepository adminRepository;
	private AdminMapper adminMapper;

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
}
