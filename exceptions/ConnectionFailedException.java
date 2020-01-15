package com.bank.exceptions;

public class ConnectionFailedException extends Exception {

  private static final long serialVersionUID = 1L;
  
  //TODO Implement this exception
  public ConnectionFailedException() {
  	super();
	}

  public ConnectionFailedException(String message) {
		super(message);
	}
}
