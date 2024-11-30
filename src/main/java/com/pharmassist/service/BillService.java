package com.pharmassist.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.pharmassist.entity.Bag;
import com.pharmassist.entity.Bill;
import com.pharmassist.entity.BillItem;
import com.pharmassist.entity.Medicine;
import com.pharmassist.entity.Patient;
import com.pharmassist.enums.PaymentMode;
import com.pharmassist.exception.PatientNotFoundByIdException;
import com.pharmassist.repository.AdminRepository;
import com.pharmassist.repository.BagRepository;
import com.pharmassist.repository.BillItemRepository;
import com.pharmassist.repository.BillRepository;
import com.pharmassist.repository.MedicineRepository;
import com.pharmassist.repository.PatientRepository;
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


	public BillService(BillRepository billRepository, MedicineRepository medicineRepository, BillItemRepository billItemRepository, BagRepository bagRepository, PatientRepository patientRepository, AdminRepository adminRepository) {
		super();
		this.adminRepository = adminRepository;
		this.billRepository = billRepository;
		this.billItemRepository = billItemRepository;
		this.bagRepository = bagRepository;
		this.medicineRepository = medicineRepository;
		this.patientRepository = patientRepository;
	}

	@Transactional
	public String createBill(String phoneNumber) {
		Patient patient = patientRepository.findByPhoneNumber(phoneNumber)
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
		
		return "Bill Created";
	}

	@Transactional
	public String addItemToBag(String billId,String medicineId,int quantity) {
		Bill bill = billRepository.findById(billId)
				.orElseThrow();
		Medicine medicine = medicineRepository.findById(medicineId)
				.orElseThrow(null);

		if(medicine.getStockQuantity()<quantity)
			throw null;
		
		BillItem billItem = new BillItem();
		billItem.setDosage(medicine.getDosageInMg()+ " mg");
		billItem.setName(medicine.getName());
		billItem.setPricePerItem(medicine.getPrice());
		billItem.setQuantity(quantity);
		billItem.setTotalPrice(medicine.getPrice()*quantity);
		billItem.setMedicine(medicine);
		medicine.setStockQuantity(medicine.getStockQuantity()-quantity);

		if(bill.getBag()!=null) {
			billItem.setBag(bill.getBag());
			bill.getBag().addItem(billItem);
		}
		
		billItemRepository.save(billItem);
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
	public Bill completeBill(String billId,PaymentMode paymentMode) {
		Bill bill = billRepository.findById(billId).orElseGet(null);
		double totalAmount = bill.getBag().getBillItems().stream()
										  .mapToDouble(BillItem::getTotalPrice)
										  .sum();
		bill.setTotalAmount(totalAmount);
		double totalPayAmount = totalAmount + (totalAmount/100)*bill.getGstInPercentage();
		bill.setTotalPayableAmount(totalPayAmount);
		bill.setPaymentMode(paymentMode);
		
		
		
		bill = billRepository.save(bill);
		
//		bagRepository.delete(bill.getBag());
		
		return bill; 
		
	}







}
