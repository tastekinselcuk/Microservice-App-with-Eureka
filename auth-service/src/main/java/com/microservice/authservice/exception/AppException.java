package com.microservice.authservice.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
	
    private HttpStatus httpStatus;
    private String title;
    private String logMessage;
    
	public AppException(HttpStatus httpStatus, String title, String logMessage) {
		super();
		this.httpStatus = httpStatus;
		this.title = title;
		this.logMessage = logMessage;
	}
	
	public AppException(HttpStatus httpStatus, String title, String message, String logMessage) {
		super();
		this.httpStatus = httpStatus;
		this.title = title;
		this.logMessage = logMessage;
	}

}
