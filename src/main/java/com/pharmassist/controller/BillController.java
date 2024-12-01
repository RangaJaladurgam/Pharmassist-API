package com.pharmassist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.pharmassist.responsedto.BillResponse;
import com.pharmassist.service.BillService;
import com.pharmassist.service.PdfService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ResponseStructure;
import com.pharmassist.util.SimpleResponseStructure;
import com.pharmassist.util.UpdateQuantityRequest;

import jakarta.servlet.http.HttpServletResponse;


@RestController
public class BillController {
	
	@Autowired
    private PdfService pdfService;
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
	public ResponseEntity<ResponseStructure<BillResponse>> completeBill(@PathVariable String billId,@RequestParam PaymentMode paymentMode){
		BillResponse bill = billService.completeBill(billId,paymentMode);
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
	
	@GetMapping("/bills/find/{billId}")
	public ResponseEntity<ResponseStructure<Bill>> getBill(@PathVariable String billId){
		Bill bill = billService.getBill(billId);
		return response.success(HttpStatus.FOUND, "bill found", bill);
	}


    @GetMapping("/bills/{billId}/generate")
    public void generateZomatoBillPdf(@PathVariable String billId,HttpServletResponse response) throws Exception {
    	Bill bill = billService.getBill(billId);
    	
        // Sample data for the Zomato bill
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", bill.getBillId());
        data.put("customerName", bill.getPatient().getName());
        data.put("customerMobile", bill.getPatient().getPhoneNumber());
        data.put("pharmacyname", bill.getPatient().getPharmacy().getName());
        
        List<Map<String, Object>> items = bill.getBag().getBillItems().stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", item.getName());
                    map.put("quantity", item.getQuantity());
                    map.put("priceperitem", item.getPricePerItem());
                    map.put("price", item.getTotalPrice());
                    return map;
                })
                .collect(Collectors.toList());


        data.put("items", items);
        
        
        data.put("totalAmount", bill.getTotalAmount());
        data.put("totalPayAmount", bill.getTotalPayableAmount());
        data.put("paymentMode",bill.getPaymentMode());
        data.put("dateTime", bill.getDateTime());

        // Generate PDF
        byte[] pdfBytes = pdfService.generatePdf("pharmacy-bill", data);

        
        // Set response headers
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pharmacy-bill.pdf");
        response.getOutputStream().write(pdfBytes);
    }
	
	
	
	
}
