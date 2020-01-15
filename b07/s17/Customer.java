package com.bank.b07.s17;

import java.util.List;
import java.util.ArrayList;

public class Customer extends User {
	private int id = 0;
	private int age = 0;
	private int roleId;
	private String address, name;
	private boolean authenticated;
	List<Account> accounts = new ArrayList<Account>();
	
	public Customer(int id, String name, int age, String address) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
	public Customer(int id, String name, int age, String address, boolean authenticated) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.authenticated = authenticated;
	}
	@Override
	public int getId() {
		return id;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}
}
