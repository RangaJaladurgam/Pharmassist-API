package com.pharmassist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmassist.entity.Admin;
import com.pharmassist.entity.Pharmacy;

public interface AdminRepository extends JpaRepository<Admin, String>{

	@Query("SELECT a.pharmacy FROM Admin a WHERE a.adminId = :adminId")
    public Optional<Pharmacy> findPharmacyByAdminId(@Param("adminId") String adminId);
}
