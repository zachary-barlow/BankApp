package com.bank.exceptions;

public class InvalidCustomerInfoException extends Exception {
	private static final long serialVersionUID = 1L;
	//TODO Implement this exception
	public InvalidCustomerInfoException() {
	    super();
    }

	public InvalidCustomerInfoException(String message) {
		super(message);
    }
}
