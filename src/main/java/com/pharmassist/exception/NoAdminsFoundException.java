package com.pharmassist.exception;


public class NoAdminsFoundException extends RuntimeException{
	
	private String message;

	public NoAdminsFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
