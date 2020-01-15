package com.bank.databasehelper;

import com.bank.b07.s17.*;
import com.bank.database.DatabaseDriver;
import com.bank.database.DatabaseInserter;
import com.bank.exceptions.ContainedException;
import com.bank.exceptions.IllegalAmountException;

import java.sql.SQLException;
import com.bank.generics.*;



import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;


public class DatabaseInsertHelper extends DatabaseInserter {
	  /**
	   * Insert a new account into account table.
	   * @param name the name of the account.
	   * @param balance the balance currently in account.
	   * @param typeId the id of the type of the account.
	   * @param connection the database connection.
	   * @return accountId of inserted account, -1 otherwise
	   */
  public static int insertAccount(String name, BigDecimal balance, int typeId) throws SQLException, IllegalAmountException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> accounts = DatabaseSelectHelper.getAccountTypesIds();
    balance = balance.setScale(2, RoundingMode.HALF_UP);
    
    // check balance if it > 0
    if(balanceChecker(balance)) {
    	// if accounts contains the type the insert it
        if(accounts.contains(typeId)) {
            int id = DatabaseInserter.insertAccount(name, balance, typeId, connection);
            connection.close();
            return id;
       }	
    } else {
    	throw new IllegalAmountException("The balance desired to be inserted is not acceptable. Balance must be greater than 0.");
    }
    return -1;
  }
  /**
   * insert an accountType into the accountType table.
   * @param name the name of the type of account.
   * @param interestRate the interest rate for this type of account.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  public static boolean insertAccountType(String name, BigDecimal interestRate) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code:
	  Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();	
	  boolean result = false;
	  // Loop through enums to see if the name is one of them, if so then insert it
	  for(AccountTypes c : AccountTypes.values()){
		if(c.name().equals(name)) {
			if(interestChecker(interestRate)) {
			    result = DatabaseInserter.insertAccountType(name, interestRate, connection);
			    connection.close();
			}
		}
	}
	return result;
  }
  /**
   * Use this to insert a new user.
   * @param name the user's name.
   * @param age the user's age.
   * @param address the user's address.
   * @param roleId the user's role.
   * @param password the user's password (not hashsed).
   * @param connection the database connection.
   * @return the account id if successful, -1 otherwise
   */
  public static int insertNewUser(String name, int age, String address, int roleId, String password) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code:
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> roles = DatabaseSelectHelper.getRoles();
    
    // iF address length < 100 and roles is new the add the user
    if((address.length() < 100) && (DatabaseInsertHelper.userRoleId(roles, roleId))) {
        int role = DatabaseInserter.insertNewUser(name, age, address, roleId, password, connection);
        connection.close();
        return role;
    }
    return -1;
  }
  /**
   * Use this to insert new roles into the database.
   * @param role the new role to be added.
   * @param connection the database.
   * @return true if successful, false otherwise.
   */
  public static boolean insertRole(String role) throws SQLException, ContainedException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code:
	  Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	  try {
		  // loop through roles enums to see if role is there
	      for(Roles r : Roles.values()){
			  if(r.name().equals(role)) {
				  // if the roles already contain the role
				  if(DatabaseSelectHelper.getRoles().contains(DatabaseInsertHelper.getRoleId(role))) {
					  throw new ContainedException("This role already exists.");
				  }
				  // else insert the role
				  DatabaseInserter.insertRole(role, connection);
				  connection.close();
				  return true;
			  }
		  }
	  } catch(SQLException e) {
		  System.out.println(":(");
	  }
      return false;
  }
  /**
   * insert a user and account relationship.
   * @param userId the id of the user.
   * @param accountId the id of the account.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  public static boolean insertUserAccount(int userId, int accountId) {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code:
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean result = false;
    
    try {
    	// insert the account
        result = DatabaseInserter.insertUserAccount(userId, accountId, connection);
        connection.close();	
    } catch(SQLException e) {
    	e.printStackTrace();
    }
    // return if pass or fail
    return result;
  }
  
  private static boolean interestChecker(BigDecimal rate) {
	  BigDecimal big1 = new BigDecimal(1.0);
	  BigDecimal big2 = new BigDecimal(0);
	  
	  int one = rate.compareTo(big1);
	  int two = rate.compareTo(big2);
	  
	  if(one == -1 && (two == 1 || two == 0)) {
		  return true;
	  } else {
		  return false;
	  }
  }
  
  private static boolean balanceChecker(BigDecimal balance) {
	  BigDecimal big1 = new BigDecimal(0);
	  
	  int amount = balance.compareTo(big1);
	  
	  if(amount >= 0) {
		  return true;
	  }
	  return false;
  }
  
  private static boolean userRoleId(List<Integer> role, int id) {
	  for(int i = 0; i < role.size(); i++) {
		  if(role.get(i) == id) {
			  return true;
		  }
	  }
	  return false;
  }
  private static int getRoleId(String role) throws SQLException  {
      //TODO Implement this method as stated on the assignment sheet (Strawberry)
      //hint: You should be using these three lines in your final code
  	List<Integer> roles =DatabaseSelectHelper.getRoles();
  	
  	// Loop through the size of roles and return the role id if role is there
  	for(int i=0; i < roles.size(); i++) {
  		if(role.equalsIgnoreCase(DatabaseSelectHelper.getRole(roles.get(i)))) {
  			return roles.get(i);
  		}
  	}
		return -1;
    }

}
