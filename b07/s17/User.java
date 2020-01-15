package com.bank.b07.s17;
import com.bank.security.PasswordHelpers;

import java.sql.SQLException;

import com.bank.databasehelper.DatabaseSelectHelper;

public abstract class User {
	private int id, roleId, age;
	private String name, address;
	private boolean authenticated = false;
	
	
	public abstract int getId();
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getRoleId() {
		return this.roleId;
	}
	
	public final boolean autheticate(String password) {
		authenticated = PasswordHelpers.comparePassword(DatabaseSelectHelper.getPassword(this.getId()), password);
		return authenticated;
	}
}
