package com.pharmassist.controller;

import org.springframework.stereotype.Controller;

import com.pharmassist.service.AdminService;
import com.pharmassist.util.AppResponseBuilder;

@Controller
public class AdminController {

	private final AdminService adminService;
	private final AppResponseBuilder response;

	public AdminController(AdminService adminService,AppResponseBuilder response) {
		this.adminService = adminService;
		this.response = response;
	}

}
