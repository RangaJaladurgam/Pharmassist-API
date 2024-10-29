package com.pharmassist.service;

import com.pharmassist.mapper.AdminMapper;
import com.pharmassist.repository.AdminRepository;

public class AdminService {
	
	private AdminRepository adminRepository;
	private AdminMapper adminMapper;

	public AdminService(AdminRepository adminRepository,AdminMapper adminMapper) {
		this.adminRepository = adminRepository;
		this.adminMapper = adminMapper;
	}
}
