package com.pharmassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmassist.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {

}
