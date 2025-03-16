package com.pharmassist.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.pharmassist.responsedto.AdminResponse;
import com.pharmassist.responsedto.BillResponse;
import com.pharmassist.service.BillService;
import com.pharmassist.service.PdfService;
import com.pharmassist.util.AppResponseBuilder;
import com.pharmassist.util.ErrorStructure;
import com.pharmassist.util.ResponseStructure;
import com.pharmassist.util.SimpleResponseStructure;
import com.pharmassist.util.UpdateQuantityRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
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
	
	@Operation(description = "The End-point can be used to create the Bill",
			responses = {
					@ApiResponse(responseCode = "201",description = "Bill Created"),
					@ApiResponse(responseCode = "404",description = "Request not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}
							)
						}
			)
	@PostMapping("bills/create")
	public ResponseEntity<ResponseStructure<BillResponse>> createBill(@RequestParam("phoneNumber") String phoneNumber){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		BillResponse bill = billService.createBill(email,phoneNumber);
		return response.success(HttpStatus.OK, "Bill Created",bill);
	}
	
	@Operation(description = "The End-point can be used to add the item to the bag",
			responses = {
					@ApiResponse(responseCode = "200",description = "Item Added"),
					@ApiResponse(responseCode = "404",description = "Request not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}
							)
						}
			)
	@PostMapping("/bills/{billId}/add-item")
    public ResponseEntity<SimpleResponseStructure> addItemToBag(@PathVariable String billId, @RequestParam String medicineId,@RequestParam int quantity) {
        String msg = billService.addItemToBag(billId, medicineId,quantity);
        return response.success(HttpStatus.OK, msg);
    }
	
	@Operation(description = "The End-point can be used to complete the bill",
			responses = {
					@ApiResponse(responseCode = "200",description = "Bill Completed"),
					@ApiResponse(responseCode = "404",description = "Request not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}
							)
						}
			)
	@PostMapping("/bills/{billId}")
	public ResponseEntity<ResponseStructure<BillResponse>> completeBill(@PathVariable String billId,@RequestParam PaymentMode paymentMode){
		BillResponse bill = billService.completeBill(billId,paymentMode);
		return response.success(HttpStatus.OK, "Bill Generated Successfully",bill);
	}
	
	@Operation(description = "The End-point can be used to remove the item from the bag",
			responses = {
					@ApiResponse(responseCode = "200",description = "Item removed"),
					@ApiResponse(responseCode = "404",description = "Request not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}
							)
						}
			)
	@DeleteMapping("/bills/{billId}/remove-item/{billItemId}")
	public ResponseEntity<SimpleResponseStructure> removeItemFromBag(@PathVariable String billId,@PathVariable String billItemId){
		String message = billService.removeItemFromBag(billId,billItemId);
		return response.success(HttpStatus.OK, message);
	}
	
	@Operation(description = "The End-point can be used to update item quantity",
			responses = {
					@ApiResponse(responseCode = "200",description = "Quantity updated"),
					@ApiResponse(responseCode = "404",description = "Request not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}
							)
						}
			)
	@PatchMapping("/bills/{billId}/update-item/{billItemId}")
	public ResponseEntity<SimpleResponseStructure> updateItemQuantity(@PathVariable String billId,
																	 @PathVariable String billItemId,
																	@RequestBody UpdateQuantityRequest quantity){
		String message = billService.updateItemQuantity(billId,billItemId,quantity);
		return response.success(HttpStatus.OK, message);
	}
	
	@Operation(description = "The End-point can be used to find the bill",
			responses = {
					@ApiResponse(responseCode = "302",description = "Bill found"),
					@ApiResponse(responseCode = "404",description = "Request not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}
							)
						}
			)
	@GetMapping("/bills/find/{billId}")
	public ResponseEntity<ResponseStructure<Bill>> getBill(@PathVariable String billId){
		Bill bill = billService.getBill(billId);
		return response.success(HttpStatus.FOUND, "bill found", bill);
	}

	
	
	@GetMapping("/pharmacy/bills/find-all")
	public ResponseEntity<ResponseStructure<List<BillResponse>>> findAllBillsByPharmacy(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		List<BillResponse> bills= billService.findAllBillsByPharmacy(email);
		return response.success(HttpStatus.FOUND, "Bills found", bills);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Operation(description = "The End-point can be used to generate bill pdf",
			responses = {
					@ApiResponse(responseCode = "200",description = "PDF Created"),
					@ApiResponse(responseCode = "404",description = "Request not found",
							content = {
									@Content(schema = @Schema(implementation = ErrorStructure.class))
							}
							)
						}
			)
    @GetMapping("/bills/{billId}/generate")
    public void generateBillPdf(@PathVariable String billId,HttpServletResponse response) throws Exception {
    	Bill bill = billService.getBill(billId);
    	
       
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
        double gstAmount = BigDecimal.valueOf(bill.getTotalPayableAmount() - bill.getTotalAmount())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        data.put("gst",gstAmount);
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
