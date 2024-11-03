package com.pharmassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmassist.entity.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, String>{
	
}
