package com.pharmassist.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Bag;
import com.pharmassist.repository.BagRepository;
import com.pharmassist.repository.PharmacyRepository;

@Service
public class BagService {

	
	private final BagRepository bagRepository;
	private final PharmacyRepository pharmacyRepository;

	public BagService(BagRepository bagRepository, PharmacyRepository pharmacyRepository) {
		super();
		this.bagRepository = bagRepository;
		this.pharmacyRepository = pharmacyRepository;
	}
	
	public Bag addBag(String pharmacyId) {
		Bag bag = new Bag();
		return pharmacyRepository.findById(pharmacyId)
						.map(pharmacy -> {
							bag.setPharmacy(pharmacy);
							return bagRepository.save(bag);
						})
						.orElseThrow();
    }


    
}
