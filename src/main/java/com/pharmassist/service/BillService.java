package com.pharmassist.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Bag;
import com.pharmassist.entity.Bill;
import com.pharmassist.entity.BillItem;
import com.pharmassist.entity.Medicine;
import com.pharmassist.entity.Patient;
import com.pharmassist.enums.PaymentMode;
import com.pharmassist.exception.AdminNotFoundByIdException;
import com.pharmassist.exception.PatientNotFoundByIdException;
import com.pharmassist.mapper.BillMapper;
import com.pharmassist.repository.AdminRepository;
import com.pharmassist.repository.BagRepository;
import com.pharmassist.repository.BillItemRepository;
import com.pharmassist.repository.BillRepository;
import com.pharmassist.repository.MedicineRepository;
import com.pharmassist.repository.PatientRepository;
import com.pharmassist.responsedto.BillResponse;
import com.pharmassist.util.UpdateQuantityRequest;

import jakarta.transaction.Transactional;

@Service
public class BillService {
	private final AdminRepository adminRepository;
	private final BillRepository billRepository;
	private final BillItemRepository billItemRepository;
	private final BagRepository bagRepository;
	private final MedicineRepository medicineRepository;
	private final PatientRepository patientRepository;
	private final BillMapper billMapper;


	public BillService(BillRepository billRepository, MedicineRepository medicineRepository, BillItemRepository billItemRepository, BagRepository bagRepository, PatientRepository patientRepository, AdminRepository adminRepository, BillMapper billMapper) {
		super();
		this.adminRepository = adminRepository;
		this.billRepository = billRepository;
		this.billItemRepository = billItemRepository;
		this.bagRepository = bagRepository;
		this.medicineRepository = medicineRepository;
		this.patientRepository = patientRepository;
		this.billMapper = billMapper;
	}

	@Transactional
	public BillResponse createBill(String email,String phoneNumber) {
		String pharmacyId = adminRepository.findByEmail(email)
				.orElseThrow(() -> new AdminNotFoundByIdException("Admin not found"))
				.getPharmacy()
				.getPharmacyId();
		Patient patient = patientRepository.findByPhoneNumber(pharmacyId,phoneNumber)
				.orElseThrow(()-> new PatientNotFoundByIdException("Patient Not found by phoneNumber"));
		
		Bill bill = new Bill();
		bill.setPatient(patient);
		bill.setDateTime(LocalDateTime.now());
		bill.setGstInPercentage(18.0);

		Bag bag = new Bag();
		bag.setPharmacy(patient.getPharmacy());
		bag.setBill(bill);
		bill.setBag(bag);
		bill.getPatient().getBills().add(bill);
		bagRepository.save(bag);
		billRepository.save(bill);
		
		return billMapper.mapToBillResponse(bill);
	}

	@Transactional
	public String addItemToBag(String billId,String medicineId,int quantity) {
		Bill bill = billRepository.findById(billId)
				.orElseThrow();
		Medicine medicine = medicineRepository.findById(medicineId)
				.orElseThrow(null);

		if(medicine.getStockQuantity()<quantity)
			throw null;
		
		 // Retrieve the bag from the bill
	    Bag bag = bill.getBag();
	    if (bag == null) {
	        throw new RuntimeException("Bag not found for the bill");
	    }

	    // Check if the item already exists in the bag
	    BillItem existingItem = bag.getBillItems().stream()
	            .filter(item -> item.getMedicine().getMedicineId().equals(medicineId))
	            .findFirst()
	            .orElse(null);

	    if (existingItem != null) {
	        // Update the existing item
	        existingItem.setQuantity(existingItem.getQuantity() + quantity);
	        existingItem.setTotalPrice(existingItem.getQuantity() * existingItem.getPricePerItem());
	    } else {
	        // Create a new BillItem if it doesn't exist
	        BillItem newItem = new BillItem();
	        newItem.setDosage(medicine.getDosageInMg() + " mg");
	        newItem.setName(medicine.getName());
	        newItem.setPricePerItem(medicine.getPrice());
	        newItem.setQuantity(quantity);
	        newItem.setTotalPrice(medicine.getPrice() * quantity);
	        newItem.setMedicine(medicine);
	        newItem.setBag(bag);
	        bag.addItem(newItem);
	    }

	    // Update the stock quantity of the medicine
	    medicine.setStockQuantity(medicine.getStockQuantity() - quantity);
	    
	    // Save changes to the repositories
	    billItemRepository.saveAll(bag.getBillItems());
	    medicineRepository.save(medicine);
	    return "Item added Successfully";
	}

	public String removeItemFromBag(String billId, String billItemId) {
		Bill bill = billRepository.findById(billId)
				.orElseThrow();
		BillItem billItem = billItemRepository.findById(billItemId)
				.orElseThrow();

		Bag bag = bill.getBag();

		bag.removeItem(billItem);
		billItemRepository.delete(billItem);
		
		Medicine medicine = billItem.getMedicine();
		medicine.setStockQuantity(medicine.getStockQuantity()+billItem.getQuantity());
		medicineRepository.save(medicine);
		return "Item has removed Successfully";
	}

	@Transactional
	public String updateItemQuantity(String billId, String billItemId, UpdateQuantityRequest quantity) {
		
		Bill bill = billRepository.findById(billId)
				.orElseThrow(null);
		BillItem billItem = billItemRepository.findById(billItemId)
				.orElseThrow(null);
		Medicine medicine = billItem.getMedicine();
		if(medicine.getStockQuantity() < quantity.getQuantity())
			return null;
		
		// Calculate the quantity difference
	    int oldQuantity = billItem.getQuantity();
	    int quantityDifference = quantity.getQuantity() - oldQuantity;

	    // Check if the new stock level is valid
	    if (medicine.getStockQuantity() < quantityDifference) {
	        throw new IllegalArgumentException("Insufficient stock for medicine: " + medicine.getName());
	    }

	    // Update medicine stock and bill item quantity
	    billItem.setQuantity(quantity.getQuantity()); // Update the bill item quantity
		billItem.setTotalPrice(billItem.getPricePerItem()*quantity.getQuantity());
	    medicine.setStockQuantity(medicine.getStockQuantity() - quantityDifference); // Adjust the stock

		billItem.setBillItemId(billItemId);

		medicineRepository.save(medicine);
		billItemRepository.save(billItem);
		return "updated quantity";
	}

	public Bill getBill(String billId) {
		return billRepository.findById(billId).orElseThrow(null);
	}

	@Transactional
	public BillResponse completeBill(String billId,PaymentMode paymentMode) {
		Bill bill = billRepository.findById(billId).orElseGet(null);
		double total = bill.getBag().getBillItems().stream()
										  .mapToDouble(BillItem::getTotalPrice)
										  .sum();
		double totalAmount = BigDecimal.valueOf(total)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
		bill.setTotalAmount(totalAmount);
		double totalPayAmount = totalAmount + (totalAmount/100)*bill.getGstInPercentage();
		totalPayAmount = BigDecimal.valueOf(totalPayAmount)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
		bill.setTotalPayableAmount(totalPayAmount);
		bill.setPaymentMode(paymentMode);
		
		
		
		bill = billRepository.save(bill);
		
//		bagRepository.delete(bill.getBag());
		
		return billMapper.mapToBillResponse(bill); 
		
	}







}
