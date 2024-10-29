package com.pharmassist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pharmassist.repository.AdminRepository;
import com.pharmassist.requestdto.AdminRequest;
import com.pharmassist.responsedto.AdminResponse;
import com.pharmassist.service.AdminService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;

@Controller
public class AdminController {

	private final AdminService adminService;
	private final AppResponseBuilder response;

	public AdminController(AdminService adminService,AppResponseBuilder response) {
		this.adminService = adminService;
		this.response = response;
	}
	
	@PostMapping("/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(@RequestBody AdminRequest adminRequest){
		AdminResponse adminResponse = adminService.saveUser(adminRequest);
		return response.success(HttpStatus.CREATED, "Admin Created", adminResponse);
	}

}
