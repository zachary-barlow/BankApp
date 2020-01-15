package com.bank.atm;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

import com.bank.b07.s17.*;
import com.bank.databasehelper.*;
import com.bank.exceptions.IllegalAmountException;
import com.bank.exceptions.InvalidCustomerInfoException;
import com.bank.exceptions.UnAuthenticatedException;

public class TellerTerminal extends ATM {
	private Teller currentUser;
	private boolean currentUserAuthenticated = false;
	private Customer currentCustomer;
	private boolean currentCustomerAuthenticated = false;
	
	
	public TellerTerminal(int tellerId, String password) throws UnAuthenticatedException {
		super(-1);
		
		try {
			// get the teller info
			currentUser = (Teller) DatabaseSelectHelper.getUserDetails(tellerId);
			// if the teller's info is right
			if(currentUser.autheticate(password)) {
				currentUserAuthenticated = true;
			} else {
				currentCustomer = null;
				throw new UnAuthenticatedException("Invalid information. Unable to authenticate access.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean makeNewAccount(String name, BigDecimal balance, int type) throws IllegalAmountException {
		// if both teller and customer are authenticated
		if(currentCustomerAuthenticated && currentUserAuthenticated) {		
			try {
				// round balance
				balance = balance.setScale(2, RoundingMode.HALF_UP);
				// check if balance > 0, if so then make the account
				if(balanceChecker(balance)) {
					int accountId = DatabaseInsertHelper.insertAccount(name, balance, type);					
					currentCustomer.addAccount(this.getAccount(type, name, balance, accountId));
					return DatabaseInsertHelper.insertUserAccount(currentCustomer.getId(), accountId);
				} else {
					throw new IllegalAmountException("Balance must be greater than 0 when starting a new Account.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}
		return false;
	}
	
	public void setCurrentCustomer(Customer customer) {
		currentCustomer = customer;
	}
	
	public void authenticateCurrentCustomer(String password) {
		try {
			if(currentCustomer.autheticate(password)) {
				currentCustomerAuthenticated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void makeNewUser(String name, int age, String address, String password) {
		try {
			// if the user is authenticated already
			if(currentUserAuthenticated) {
				// then make the user according to the info given
			    int id = DatabaseInsertHelper.insertNewUser(name, age, address, this.getUserRoleId(), password);
			    Customer newCustomer = new Customer(id, name, age, address);
			    currentCustomer = newCustomer;
			    System.out.println("Id: " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void giveInterest(int accountId) throws SQLException {
		// if both user and customer are authenticated
		if(currentUserAuthenticated && currentCustomerAuthenticated) {
			// if the accounts of the customer contains the account id
			if(DatabaseSelectHelper.getAccountIds(currentCustomer.getId()).contains(accountId)) {
				List<Account> custAccounts = currentCustomer.getAccounts();
				this.giveInterestHelper(custAccounts, accountId);
			}	
		}
	}
	
	public void deAuthenticateCustomer() {
		currentCustomerAuthenticated = false;
		currentCustomer = null;
	}
	
	private boolean balanceChecker(BigDecimal balance) {
		BigDecimal big1 = new BigDecimal(0);
		  
		int amount = balance.compareTo(big1);
		  
		if(amount >= 0) {
			return true;
		}
		return false;
	  }
	
	private Account getAccount(int type, String name, BigDecimal balance, int accountId) {
		String accountName;
		Account newAccount = null;
		try {
			// return the type of the account given the type
			// set it to actual account
			accountName = DatabaseSelectHelper.getAccountTypeName(type);
			if(accountName.toUpperCase().equals("CHEQUING")) {
				newAccount = new ChequingAccount(accountId, name, balance);
			} else if (accountName.toUpperCase().equals("SAVING")) {
				newAccount = new SavingsAccount(accountId, name, balance);
			} else if (accountName.toUpperCase().equals("TFSA")) {
				newAccount = new Tfsa(accountId, name, balance);
			} else {
				
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return newAccount;
	}
	
	private void giveInterestHelper(List<Account> accounts, int id) throws SQLException {
		String name = null;
		Account account;
		for(int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i).getId() == id) {
				account = accounts.get(i);
				name = accounts.get(i).getName();
				
				if(name.equalsIgnoreCase("Chequing")) {
					((ChequingAccount) account).findAndSetInterestRate();
					((ChequingAccount) account).addInterest();
				} else if(name.equalsIgnoreCase("Savings")) {
					((SavingsAccount) account).findAndSetInterestRate();
					((SavingsAccount) account).addInterest();
				} else {
					((Tfsa) account).findAndSetInterestRate();
					((Tfsa) account).addInterest();
				}
			}
		}
	}
	
	private int getUserRoleId() throws SQLException {
		List<Integer> roles = DatabaseSelectHelper.getRoles();
		
		for(int i = 0; i < roles.size(); i++) {
			if(DatabaseSelectHelper.getRole(i).equalsIgnoreCase("Chequing")) {
				return i;
			}
		}
		return roles.size();
	}
	
}
