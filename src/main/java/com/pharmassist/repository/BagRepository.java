package com.pharmassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmassist.entity.Bag;

public interface BagRepository extends JpaRepository<Bag, String>{

}
