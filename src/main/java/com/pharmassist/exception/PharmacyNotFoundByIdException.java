package com.pharmassist.exception;

public class PharmacyNotFoundByIdException extends RuntimeException{
	
	private String message;

	public PharmacyNotFoundByIdException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
