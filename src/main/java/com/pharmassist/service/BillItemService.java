package com.pharmassist.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Bag;
import com.pharmassist.entity.BillItem;
import com.pharmassist.repository.BagRepository;
import com.pharmassist.repository.BillItemRepository;
import com.pharmassist.repository.MedicineRepository;

@Service
public class BillItemService {
	
	
	private final BillItemRepository billItemRepository;
	private final MedicineRepository medicineRepository;
	private final BagRepository bagRepository;
	
	

	public BillItemService(BillItemRepository billItemRepository, MedicineRepository medicineRepository, BagRepository bagRepository) {
		super();
		this.billItemRepository = billItemRepository;
		this.medicineRepository = medicineRepository;
		this.bagRepository = bagRepository;
	}



	public BillItem addBillItem(String bagId,String medicineId,int quantity) {
		
		Optional<Bag> optional = bagRepository.findById(bagId);
		if(optional.isEmpty())
			return null;
		else {	
			Bag bag = optional.get();
			return medicineRepository.findById(medicineId)
							.map((medicine)->{
								BillItem billItem = new BillItem();
								billItem.setQuantity(quantity);
								billItem.setPricePerItem(medicine.getPrice());
								billItem.setTotalPrice(medicine.getPrice() * quantity);
								billItem.setMedicine(medicine);
								
								billItem.setBag(bag);
								
								billItemRepository.save(billItem);
								if(bag.getBillItems() == null)
									bag.setBillItems(new ArrayList<>());
								
								bag.getBillItems().add(billItem);
								return billItem;	
							})
							.orElseThrow();	
		}
	}
	
	

}
