package com.pharmassist.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;

	public AdminService(AdminRepository adminRepository,AdminMapper adminMapper, PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.adminMapper = adminMapper;
		this.passwordEncoder = passwordEncoder;
	}

	public AdminResponse saveAdmin(AdminRequest adminRequest) {
		
		Admin admin = adminMapper.mapToAdmin(adminRequest, new Admin());
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		admin = adminRepository.save(admin);
		
		return adminMapper.mapToAdminResponse(admin);
	}

	public AdminResponse findAdmin(String email) {
		
		return adminRepository.findByEmail(email)
						.map((admin)-> adminMapper.mapToAdminResponse(admin))
						.orElseThrow(()-> new AdminNotFoundByIdException("Failed to find the Admin"));
	}

	public AdminResponse updateAdmin(String email, AdminRequest adminRequest) {
		return adminRepository.findByEmail(email)
						.map((exAdmin)->{
							exAdmin = adminMapper.mapToAdmin(adminRequest, exAdmin);
							exAdmin.setPassword(passwordEncoder.encode(exAdmin.getPassword()));
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
