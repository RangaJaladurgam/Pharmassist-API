package com.pharmassist.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pharmassist.requestdto.AdminRequest;
import com.pharmassist.responsedto.AdminResponse;
import com.pharmassist.service.AdminService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;

import jakarta.validation.Valid;

@Controller
public class AdminController {

	private final AdminService adminService;
	private final AppResponseBuilder response;

	public AdminController(AdminService adminService,AppResponseBuilder response) {
		this.adminService = adminService;
		this.response = response;
	}
	
	@PostMapping("/admins")
	public ResponseEntity<ResponseStructure<AdminResponse>> saveAdmin(@RequestBody @Valid AdminRequest adminRequest){
		AdminResponse adminResponse = adminService.saveUser(adminRequest);
		return response.success(HttpStatus.CREATED, "Admin Created", adminResponse);
	}
	
	@GetMapping("/admins/{adminId}")
	public ResponseEntity<ResponseStructure<AdminResponse>> findAdmin(@PathVariable String adminId){
		AdminResponse adminResponse = adminService.findAdmin(adminId);
		return response.success(HttpStatus.FOUND,"Admin found by Id", adminResponse);
	}
	
	@PutMapping("/admins/{adminId}")
	public ResponseEntity<ResponseStructure<AdminResponse>> updateAdmin(@PathVariable String adminId,@RequestBody AdminRequest adminRequest){
		AdminResponse adminResponse = adminService.updateAdmin(adminId,adminRequest);
		return response.success(HttpStatus.OK, "Admin Updated", adminResponse);
	}
	
	@GetMapping("/admins")
	public ResponseEntity<ResponseStructure<List<AdminResponse>>> findAllAdmins(){
		List<AdminResponse> adminResponses = adminService.findAllAdmins();
		return response.success(HttpStatus.FOUND, "Admins Found", adminResponses);
	}

}
