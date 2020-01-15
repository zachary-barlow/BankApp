package com.bank.atm;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import com.bank.b07.s17.*;
import com.bank.databasehelper.DatabaseSelectHelper;
import com.bank.exceptions.UnAuthenticatedException;
import com.bank.exceptions.InvalidCustomerInfoException;
import com.bank.generics.AccountTypes;

public class ATM {
	private Customer currentCustomer;
	private boolean authenticated = false;
	
	public ATM(int customerId, String password) throws InvalidCustomerInfoException {
		try {
			if(DatabaseSelectHelper.getPassword(customerId).equals(password)) {
				authenticated = true;
				currentCustomer = (Customer) DatabaseSelectHelper.getUserDetails(customerId);
			} else {
				throw new InvalidCustomerInfoException("Password or does not match the customer's password with given id.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ATM(int customerId) {
		try {
			currentCustomer = (Customer) DatabaseSelectHelper.getUserDetails(customerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean authenticate(int userId, String password) throws SQLException, InvalidCustomerInfoException {
		if(DatabaseSelectHelper.getPassword(userId).equals(password)) {
			authenticated = true;
			currentCustomer = (Customer) DatabaseSelectHelper.getUserDetails(userId);
		} else {
			throw new InvalidCustomerInfoException();
		}
		return authenticated;
	}
	
	public List<Account> listAccounts() {
        return currentCustomer.getAccounts();
	}
	
	public boolean makeDeposit(BigDecimal amount, int accountId) throws UnAuthenticatedException {
		Account custAccount;
		try {
		custAccount = DatabaseSelectHelper.getAccountDetails(accountId);
		if(authenticated) {
			BigDecimal balance = this.checkBalance(accountId);
			balance.add(amount);
				
			custAccount.setBalance(balance);
			return true;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public BigDecimal checkBalance(int accountId) throws UnAuthenticatedException {
		if(authenticated) {
			try {
				Account custAccount = DatabaseSelectHelper.getAccountDetails(accountId);
				return custAccount.getBalance();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		} else {
			throw new UnAuthenticatedException();
		}
		return null;
	}
	
	public boolean makeWithdrawal(BigDecimal amount, int accountId) throws UnAuthenticatedException {
		Account custAccount;
		try {
			custAccount = DatabaseSelectHelper.getAccountDetails(accountId);
			if(authenticated) {
				BigDecimal balance = this.checkBalance(accountId);
				balance.subtract(amount);
				
				custAccount.setBalance(balance);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
