package com.pharmassist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmassist.entity.Bill;
import com.pharmassist.enums.PaymentMode;
import com.pharmassist.service.BillService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;
import com.pharmassist.util.SimpleResponseStructure;
import com.pharmassist.util.UpdateQuantityRequest;


@RestController
public class BillController {
	
	private final BillService billService;
	private final AppResponseBuilder response;
	
	public BillController(BillService billService, AppResponseBuilder response) {
		super();
		this.billService = billService;
		this.response = response;
	}
	
	@PostMapping("bills/create")
	public ResponseEntity<SimpleResponseStructure> createBill(@RequestParam("phoneNumber") String phoneNumber){
		String message = billService.createBill(phoneNumber);
		return response.success(HttpStatus.OK,message);
	}
	
	@PostMapping("/{billId}/add-item")
    public ResponseEntity<SimpleResponseStructure> addItemToBag(@PathVariable String billId, @RequestParam String medicineId,@RequestParam int quantity) {
        String msg = billService.addItemToBag(billId, medicineId,quantity);
        return response.success(HttpStatus.OK, msg);
    }
	@PostMapping("/bills/{billId}")
	public ResponseEntity<ResponseStructure<Bill>> completeBill(@PathVariable String billId,@RequestParam PaymentMode paymentMode){
		Bill bill = billService.completeBill(billId,paymentMode);
		return response.success(HttpStatus.OK, "Bill Generated Successfully",bill);
	}
	
	@DeleteMapping("/bills/{billId}/remove-item/{billItemId}")
	public ResponseEntity<SimpleResponseStructure> removeItemFromBag(@PathVariable String billId,@PathVariable String billItemId){
		String message = billService.removeItemFromBag(billId,billItemId);
		return response.success(HttpStatus.OK, message);
	}
	
	@PatchMapping("/bills/{billId}/update-item/{billItemId}")
	public ResponseEntity<SimpleResponseStructure> updateItemQuantity(@PathVariable String billId,
																	 @PathVariable String billItemId,
																	@RequestBody UpdateQuantityRequest quantity){
		String message = billService.updateItemQuantity(billId,billItemId,quantity);
		return response.success(HttpStatus.OK, message);
	}
	
	@GetMapping("/bills")
	public ResponseEntity<ResponseStructure<Bill>> getBill(@PathVariable String billId){
		Bill bill = billService.getBill(billId);
		return response.success(HttpStatus.FOUND, "bill found", bill);
	}
	
	
	
	
	
}
