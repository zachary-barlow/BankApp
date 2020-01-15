package com.bank.b07.s17;

public class Teller extends User {
	private int id, age, roleId;
	private String address, name;
	private boolean authenticated;
	
	public Teller(int id, String name, int age, String address) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
	}
	
	public Teller(int id, String name, int age, String address, boolean authenticated) {
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
}
