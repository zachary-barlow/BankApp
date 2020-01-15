package com.bank.b07.s17;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.bank.databasehelper.DatabaseSelectHelper;

public class Tfsa extends Account {

    private int id, type;
    private String name;
    private BigDecimal balance, interestRate;
	
	public Tfsa(int id, String name, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}
	
	public void findAndSetInterestRate() throws SQLException {
		this.interestRate = DatabaseSelectHelper.getInterestRate(this.type);
	}
	
	public void addInterest() {
		balance.add(balance.multiply(interestRate));
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public BigDecimal getBalance() {
		return this.balance;
	}
	
	@Override
	public int getType() {
		return this.type;
	}
}
