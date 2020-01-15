package com.bank.b07.s17;

import java.math.BigDecimal;

public abstract class Account {	
	private BigDecimal balance;
	private int id;
	private String name;
	
	public abstract int getId();
	
	public void setId(int id) {
		this.id = id;
	}
	public abstract String getName();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract BigDecimal getBalance();
	
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public abstract int getType();
}
