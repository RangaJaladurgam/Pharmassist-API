package com.pharmassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmassist.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{

}
