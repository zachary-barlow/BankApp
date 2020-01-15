package com.bank.exceptions;

public class UnAuthenticatedException extends Exception {
	private static final long serialVersionUID = 1L;
	//TODO Implement this exception
	public UnAuthenticatedException() {
	    super();
    }

	public UnAuthenticatedException(String message) {
		super(message);
    }
}
