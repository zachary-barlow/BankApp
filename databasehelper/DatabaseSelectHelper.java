package com.bank.databasehelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.b07.s17.*;
import com.bank.database.DatabaseDriver;
import com.bank.database.DatabaseInserter;
import com.bank.database.DatabaseSelector;

public class DatabaseSelectHelper extends DatabaseSelector {
	  /**
	   * get the role with id id.
	   * @param id the id of the role
	   * @param connection the database connection
	   * @return a String containing the role.
	   * @throws SQLException thrown when something goes wrong with query.
	   */
  public static String getRole(int id) throws SQLException  {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String role = DatabaseSelector.getRole(id, connection);
    connection.close();
    return role;
  }
  /**
   * get the hashed version of the password.
   * @param userId the user's id.
   * @param connection the database connection.
   * @return the hashed password to be checked against given password.
   * @throws SQLException if a database issue occurs. 
   */
  public static String getPassword(int userId)  {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
	  Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	  String hashPassword = "";
	  try {
		  hashPassword = DatabaseSelector.getPassword(userId, connection);
		  connection.close();
	  } catch(SQLException e) {
		  e.printStackTrace();
	  }
    return hashPassword;
    
  }
  /**
   * find all the details about a given user.
   * @param userId the id of the user.
   * @param connection a connection to the database.
   * @return a result set with the details of the user.
   * @throws SQLException thrown when something goes wrong with query.
   */
  public static User getUserDetails(int userId) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
    User newUser = null;

    while (results.next()) {
      //It may help you to figure out the user's type, and make an obj based on its
      //System.out.println(results.getString("NAME"));
      //System.out.println(results.getInt("AGE"));
      //System.out.println(results.getString("ADDRESS"));                    
      //System.out.println(results.getInt("ROLEID"));
    	
    	// send the info to helper
      newUser = DatabaseSelectHelper.setNewUser(results.getString("NAME"), results.getInt("AGE"), userId, 
      		  results.getString("ADDRESS"), results.getInt("ROLEID"));
    }
    connection.close();
    return newUser;
  }
  /**
   * return the id's of all of a user's accounts.
   * @param userId the id of the user.
   * @param connection the connection to the database.
   * @return a result set containing all accounts.
   * @throws SQLException thrown when something goes wrong with query.
   */
  public static List<Integer> getAccountIds(int userId) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getUserDetails(userId, connection);
    List<Integer> ids = new ArrayList<>();
    
    // ass the id to the ids
    while (results.next()) {                 
      System.out.println(results.getInt("ACCOUNTID"));
      ids.add(results.getInt("ACCOUNTID"));
    }
    connection.close();
    return ids;
  }
  /**
   * get the full details of an account.
   * @param accountId the id of the account
   * @param connection the connection to the database.
   * @return the details of the account.
   * @throws SQLException thrown when something goes wrong with query.
   */
  public static Account getAccountDetails(int accountId) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAccountDetails(accountId, connection);
    Account newAccount = null;
    
    while (results.next()) {
      //It may help you to figure out the account type, and make an obj based on its
      //System.out.println(results.getString("NAME"));
      //System.out.println(results.getString("BALANCE"));                    
      //System.out.println(results.getInt("TYPE"));
    	// send the info to the helper
    	BigDecimal balance = new BigDecimal(results.getString("BALANCE").replaceAll(",", ""));
        newAccount = DatabaseSelectHelper.setNewAccount(results.getInt("TYPE"), results.getString("NAME"), balance, accountId);
    }
    
    
    return newAccount;
  }
  /**
   * return the balance in the account.
   * @param accountId the account to check.
   * @param connection a connection to the database.
   * @return the balance
   * @throws SQLException thrown when something goes wrong with query.
   */
  public static BigDecimal getBalance(int accountId) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    BigDecimal balance = DatabaseSelector.getBalance(accountId, connection);
    connection.close();
    return balance;
  }
  /**
   * Get the interest rate for an account.
   * @param accountType the type for the account.
   * @param connection the database connection.
   * @return the interest rate.
   * @throws SQLException thrown if something goes wrong with the query.
   */
  public static BigDecimal getInterestRate(int accountType) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: The below code should help you out a little
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    BigDecimal interestRate = DatabaseSelector.getInterestRate(accountType, connection);
    connection.close();
    return interestRate;
  }
  /**
   * Return all data found within the AccountTypes table.
   * @param connection the connection to the database.
   * @return a result set of all rows in the table.
   * @throws SQLException thrown if there is an issue.
   */
  public static List<Integer> getAccountTypesIds() throws SQLException {
    //TODO Implement this method to get all account Type Id's stored in the database
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getAccountTypesId(connection);
    List<Integer> ids = new ArrayList<>();
    
    // Add the id to the ids tbale
    while (results.next()) {
      System.out.println(results.getInt("ID"));
      ids.add(results.getInt("ACCOUNTID"));
    }
    connection.close();
    return ids;
  }
  /**
   * Return the accounttype name given an accountTypeId.
   * @param accountTypeId the id of the account type.
   * @param connection the connection to the database.
   * @return The name of the account type.
   * @throws SQLException thrown if something goes wrong.
   */
  public static String getAccountTypeName(int accountTypeId) throws SQLException {
    //TODO Implement this method to get the account Type Names
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    String name = DatabaseSelector.getAccountTypeName(accountTypeId, connection);
    connection.close();
    return name;
  }
  /**
   * get all the roles.
   * @param connection the connection.
   * @return a ResultSet containing all rows of the roles table.
   * @throws SQLException thrown if an SQLException occurs.
   */
  public static List<Integer> getRoles() throws SQLException {
    //TODO implement this method
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = DatabaseSelector.getRoles(connection);
    List<Integer> ids = new ArrayList<>();
    
    while (results.next()) {
      System.out.println(results.getInt("ID"));
      ids.add(results.getInt("ID"));
    }
    connection.close();
    return ids;
  }
  /**
   * get the typeId of the account.
   * @param accountId the accounts id
   * @param connection the connection to the database
   * @return the typeId
   * @throws SQLException thrown if something goes wrong.
   */
  public static int getAccountType(int accountId) throws SQLException {
    //TODO implement this method
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    int result = DatabaseSelector.getAccountType(accountId, connection);
    connection.close();
    return result;
  }
  /**
   * get the role of the given user.
   * @param userId the id of the user.
   * @param connection the connection to the database.
   * @return the roleId for the user.
   * @throws SQLException thrown if something goes wrong with the query.
   */
  public static int getUserRole(int userId) {
    //TODO implement this method
	  Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
	  try {
		    int result = DatabaseSelector.getUserRole(userId, connection);
		    connection.close();
		    return result;
		    
	  } catch(SQLException e) {
		  e.printStackTrace();
	  }
	  return -1;
  }
  
    private static Account setNewAccount(int type, String name, BigDecimal balance, int accountId) {
	    String accountName;
	    try {
	    	// get the account type name
		    accountName = DatabaseSelectHelper.getAccountTypeName(type);
		    
		    // Set the new account according to the name of the type
		    if(accountName.toUpperCase().equals("CHEQUING")) {
			    ChequingAccount newAccount = new ChequingAccount(accountId, name, balance);
			    return newAccount;
		    } else if (accountName.toUpperCase().equals("SAVING")) {
			    SavingsAccount newAccount = new SavingsAccount(accountId, name, balance);
			    return newAccount;
		    } else if (accountName.toUpperCase().equals("TFSA")) {
			    Tfsa newAccount = new Tfsa(accountId, name, balance);
			    return newAccount;
		    } else {
			
		    }	
	    } catch (SQLException e1) {
		    e1.printStackTrace();
	    }
	    return null;
    }
    
    private static User setNewUser(String name, int age, int userId, String address, int roleId) throws SQLException {
    	String role = DatabaseSelectHelper.getRole(roleId);
    	
    	// set the role of the user to the according role returned
    	if(role.equalsIgnoreCase("Customer")) {
    		Customer newCust = new Customer(userId, name, age, address);
		    return newCust;
	    } else if (role.equalsIgnoreCase("Teller")) {
	    	Teller newTeller = new Teller(userId, name, age, address);
	    	return newTeller;
	    } else if (role.equalsIgnoreCase("Admin")) {
	    	Admin newAdmin = new Admin(userId, name, age, address);
	    	return newAdmin;
	    }
    	
    	return null;
    }
    

}
