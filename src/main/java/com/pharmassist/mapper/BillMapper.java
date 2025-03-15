package com.pharmassist.mapper;

import org.springframework.stereotype.Component;

import com.pharmassist.entity.Bill;
import com.pharmassist.responsedto.BillResponse;

@Component
public class BillMapper {

	private final BagMapper bagMapper;
	private final PatientMapper patientMapper;

	public BillMapper(BagMapper bagMapper, PatientMapper patientMapper) {
		super();
		this.bagMapper = bagMapper;
		this.patientMapper = patientMapper;
	}



	public BillResponse mapToBillResponse(Bill bill) {
		BillResponse response = new BillResponse();
		response.setBillId(bill.getBillId());
		response.setGstInPercentage(bill.getGstInPercentage());
		response.setDateTime(bill.getDateTime());
		if(bill.getPaymentMode()!=null) {
			response.setPaymentMode(bill.getPaymentMode());
			response.setBagResponse(bagMapper.mapToBagResponse(bill.getBag()));
		}
		response.setTotalAmount(bill.getTotalAmount());
		response.setTotalPayableAmount(bill.getTotalPayableAmount());
		response.setPatientResponse(patientMapper.mapToPatientResponse(bill.getPatient()));
		return response;
	}
}
