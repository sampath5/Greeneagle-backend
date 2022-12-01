//package com.ecommerce.model;
//
//import org.springframework.http.HttpStatus;
//
//public class ErrorModel {
//
//	private HttpStatus httpStatus;
//	private String message;
//	private String error;
//	private Object obj;
//	
//	public ErrorModel(HttpStatus httpStatus, String message, String error) {
//		super();
//		this.httpStatus = httpStatus;
//		this.message = message;
//		this.error = error;
//	}
//
//	public ErrorModel(String field, Object rejectedValue, String defaultMessage) {
//		// TODO Auto-generated constructor stub
//	this.error=field;
//	this.obj=rejectedValue;
//	this.message=defaultMessage;
//	}
//
//	public HttpStatus getHttpStatus() {
//		return httpStatus;
//	}
//
//	public void setHttpStatus(HttpStatus httpStatus) {
//		this.httpStatus = httpStatus;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public String getError() {
//		return error;
//	}
//
//	public void setError(String error) {
//		this.error = error;
//	}
//	
//	
//}
