package com.bank.exceptions;

public class ContainedException extends Exception {
    private static final long serialVersionUID = 1L;

    public ContainedException() {
    	super();
	}

    public ContainedException(String message) {
		super(message);
	}
}
