package com.pharmassist.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AppResponseBuilder {
	
	public <T> ResponseEntity<ResponseStructure<T>> success(HttpStatus status,String message,T data){
		return ResponseEntity.status(status)
				.body(ResponseStructure.create(status, message, data));
	}
	
	public ResponseEntity<SimpleResponseStructure> success(HttpStatus status,String message) {
		return ResponseEntity.status(status)
				.body(SimpleResponseStructure.create(status, message));
	}
	
	public <T> ResponseEntity<ErrorStructure<T>> error(HttpStatus status,String message,T rootCause){
		return ResponseEntity.status(status)
				.body(ErrorStructure.create(status.value(), message, rootCause));
	}
}
