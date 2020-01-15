package com.bank.databasehelper;

import com.bank.database.DatabaseUpdater;
import com.bank.generics.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;



public class DatabaseUpdateHelper extends DatabaseUpdater{
  
  public static boolean updateRoleName(String name, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    if(DatabaseUpdateHelper.checkRoleName(name)) {
        complete = DatabaseUpdater.updateRoleName(name, id, connection);
        connection.close();
        return complete;
    }
    return complete;
  }
  
  
  public static boolean updateUserName(String name, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;

    if(DatabaseUpdateHelper.checkRoleName(name)) {
        complete = DatabaseUpdater.updateUserName(name, id, connection);
        connection.close();
        return complete;
    }

    return complete;
  }
  
  
  public static boolean updateUserAge(int age, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    if(age >= 0) {
    	complete = DatabaseUpdater.updateUserAge(age, id, connection);	
    	connection.close();
    }
    return complete;
  }
  
  public static boolean updateUserRole(int roleId, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> roles = DatabaseSelectHelper.getRoles();
    boolean complete = false;
    
    if(roles.contains(roleId)) {
    	complete = DatabaseUpdater.updateUserRole(roleId, id, connection);
        connection.close();
    }
    return complete;
  }
  
  
  public static boolean updateUserAddress(String address, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    if(address.length() <= 100) {
        complete = DatabaseUpdater.updateUserAddress(address, id, connection);
        connection.close();
    }
    return complete;
  }


  public static boolean updateAccountName(String name, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    
    if(DatabaseUpdateHelper.checkRoleName(name)) {
    	complete = DatabaseUpdater.updateAccountName(name, id, connection);
    	connection.close();
    }
    return complete;
  }
  
  public static boolean updateAccountBalance(BigDecimal balance, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    balance = balance.setScale(2, RoundingMode.HALF_UP);
    boolean complete = DatabaseUpdater.updateAccountBalance(balance, id, connection); 
    connection.close();
    return complete;
  }
  
 
  public static boolean updateAccountType(int typeId, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    List<Integer> accounts = DatabaseSelectHelper.getAccountTypesIds();
    boolean complete = false;
    
    if(accounts.contains(typeId)) {
        complete = DatabaseUpdater.updateAccountType(typeId, id, connection);    
        connection.close();
    }
    return complete;
  }
  
  
  public static boolean updateAccountTypeName(String name, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    
    if(DatabaseUpdateHelper.checkRoleName(name)) {
    	complete = DatabaseUpdater.updateAccountTypeName(name, id, connection);
        connection.close();
    }
    return complete;
  }
  
 
  public static boolean updateAccountTypeInterestRate(BigDecimal interestRate, int id) throws SQLException {
    //TODO Implement this method as stated on the assignment sheet (Strawberry)
    //hint: You should be using these three lines in your final code
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    boolean complete = false;
    if(DatabaseUpdateHelper.interestChecker(interestRate)) {
    	complete = DatabaseUpdater.updateAccountTypeInterestRate(interestRate, id, connection);
    	connection.close();
    }
    
    return complete;
  }
  
  private static boolean checkRoleName(String name) {
	  for(Roles c : Roles.values()){
		  if(c.name().equals(name)) {
			  return true;
		  }
	  }
	  return false;
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
}
