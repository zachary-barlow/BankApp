package com.bank.exceptions;

public class InsuffiecintFundsException extends Exception {

  private static final long serialVersionUID = 1L;
  //TODO Implement this exception
  public InsuffiecintFundsException() {
  	super();
	}

  public InsuffiecintFundsException(String message) {
		super(message);
	}
}
