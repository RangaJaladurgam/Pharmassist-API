package com.pharmassist.service;

import com.pharmassist.repository.AdminRepository;

public class AdminService {
	
	private AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
}
