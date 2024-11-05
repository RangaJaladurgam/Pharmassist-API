package com.pharmassist.exception;

public class NoPharmaciesFoundException extends RuntimeException {
	
	private String message;

	public NoPharmaciesFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
}
