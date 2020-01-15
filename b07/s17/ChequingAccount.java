package com.bank.b07.s17;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.bank.databasehelper.DatabaseSelectHelper;

public class ChequingAccount extends Account {
    private int id, type;
    private String name;
    private BigDecimal balance, interestRate;
	
	public ChequingAccount(int id, String name, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}
	
	public void findAndSetInterestRate() throws SQLException {
		interestRate = DatabaseSelectHelper.getInterestRate(this.type);
	}
	
	public void addInterest() {
		balance.add(balance.multiply(interestRate));
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public BigDecimal getBalance() {
		return balance;
	}
	
	@Override
	public int getType() {
		return type;
	}
}
