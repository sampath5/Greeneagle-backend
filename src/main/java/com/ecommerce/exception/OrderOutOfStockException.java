package com.ecommerce.exception;

public class OrderOutOfStockException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	public OrderOutOfStockException() {
		super();
	}
	public OrderOutOfStockException(String message) {
		super(message);
	}
}
