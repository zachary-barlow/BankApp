package com.bank.bank;

import com.bank.atm.*;
import com.bank.b07.s17.*;
import com.bank.database.DatabaseDriver;
import com.bank.databasehelper.DatabaseInsertHelper;
import com.bank.databasehelper.DatabaseSelectHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;


public class Bank {
  
  /**
   * This is the main method to run your entire program! Follow the Candy Cane instructions to
   * finish this off.
   * @param argv unused.
   */
  public static void main(String[] argv) {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    
    try {
      //ONLY UNCOMMENT THIS ON FIRST RUN!
      //DatabaseDriverExtender.initialize(connection);
      
      //TODO Check what is in argv 
      //If it is -1
    	if(argv[0].equals("-1")) {
      /*
       * TODO This is for the first run only!
       * Add this code:
       * DatabaseDriverExtender.initialize(connection);
       * Then add code to create your first account, an administrator with a password
       * Once this is done, proceed to ask the user for their input
       */
    		
    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    		System.out.print("Please enter admin's name: ");
    		System.out.print("iiiii");
    		String adminName = br.readLine();
    		System.out.print("Please enter admin's age: ");
    		int adminAge = Integer.parseInt(br.readLine());
    		System.out.print("Please enter admin's address: ");
    		String adminAddress= br.readLine();
    		System.out.print("Please enter admin's password: ");
    		String adminPassword = br.readLine();
    		
    		Admin admin = new Admin(1, adminName, adminAge, adminAddress);
    		boolean roled = DatabaseInsertHelper.insertRole("ADMIN");

    		int adminId = DatabaseInsertHelper.insertNewUser(adminName, adminAge, adminAddress, 1, adminPassword);
    		admin.setId(adminId);
    		System.out.println("User id: " + adminId);
      //If it is 1
    	} else if(argv[0].equals("1")) {
      /*
       * TODO In admin mode, the user must first login with a valid admin account
       * This will allow the user to create new Teller's.  At this point, this is
       * all the admin can do.
       */
    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    		Admin admin;
    		int adminId;
    		String adminPassword;
    		do {
        		System.out.print("Please enter valid administrator's id: ");
        		adminId = Integer.parseInt(br.readLine());
        		System.out.print("Please enter valid administrator's password: ");
        		adminPassword = br.readLine();
        		
        		admin = (Admin) DatabaseSelectHelper.getUserDetails(adminId);
    		} while(admin.autheticate(adminPassword) == false);
    		
    		
    		System.out.print("Please enter teller's name: ");
    		String tellerName = br.readLine();
    		System.out.print("Please enter teller's age: ");
    		int tellerAge = Integer.parseInt(br.readLine());
    		System.out.print("Please enter teller's address: ");
    		String tellerAddress= br.readLine();
    		System.out.print("Please enter teller's password: ");
    		String tellerPassword = br.readLine();
    		
    		Teller activeTeller = new Teller(0, tellerName, tellerAge, tellerAddress, true);
    		boolean roled = DatabaseInsertHelper.insertRole("TELLER");
    		
    		int tellerId = DatabaseInsertHelper.insertNewUser(tellerName, tellerAge, tellerAddress, 2, tellerPassword);
    		activeTeller.setId(tellerId);
    		System.out.println("Teller Id: " + tellerId);

    	
      //If anything else - including nothing
    	} else {
    	
	      /*
	       * TODO Create a context menu, where the user is prompted with:
	       * 1 - TELLER Interface
	       * 2 - ATM Interface
	       * 0 - Exit
	       * Enter Selection: 
	       */
    		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    	System.out.println("1 - Teller Interface");
	    	System.out.println("2 - ATM Interface");
	    	System.out.println("0 - Exit");
	    	System.out.print("Enter Selection: ");
	    	int input = Integer.parseInt(br.readLine());
	    	
	      //If the user entered 1
	    	if(input == 1) {
	      /*
	       * TODO Create a context menu for the Teller interface
	       * Prompt the user for their id and password
	       * Attempt to authenticate them.
	       * If the Id is not that of a Teller or password is incorrect, end the session
	       * If the Id is a teller, and the password is correct, prompt as follows:
	       * 1. authenticate new user
	       * 2. Make new User
	       * 3. Make new account
	       * 4. Give interest
	       * 5. Make a deposit
	       * 6. Make a withdrawal
	       * 7. check balance
	       * 8. close customer session
	       * 9. Exit
	       * 
	       * Continue to loop through as appropriate, ending once you get an exit code (9)
	       */
	    		Teller tell;
	    		String tellPass;
	    		int tellId;
	    		boolean authenticated = false;
	    		do {
		    		System.out.print("Please enter valid Teller id: ");
		    		tellId = Integer.parseInt(br.readLine());
		    		
		    		System.out.print("Please enter valid Teller password: ");
		    		tellPass = br.readLine();
		    		
		    		
		    		tell = (Teller) DatabaseSelectHelper.getUserDetails(tellId);
		    		if(authenticated == true) {
		    			break;
		    		}
		    		authenticated = tell.autheticate(tellPass);
	    		} while(authenticated == false);
	   
	    		
	    		TellerTerminal teller = new TellerTerminal(tellId, tellPass);
	    		boolean roled = DatabaseInsertHelper.insertRole("CUSTOMER");
	    		int condition;
	    		do {
	    			System.out.println("1. Authenticate new user");
	    			System.out.println("2. Make new User");
	    			System.out.println("3. Make new account");
	    			System.out.println("4. Give interest");
	    			System.out.println("5. Make a deposit");
	    			System.out.println("6. Make a withdrawal");
	    			System.out.println("7. check balance");
	    			System.out.println("8. close customer session");
	    			System.out.println("9. Exit");
	    			
	    			condition = Integer.parseInt(br.readLine());
	    			
	    			if(condition == 1) {
	    				// User'sid
	    				System.out.print("Please enter User's id: ");
	    				int userId = Integer.parseInt(br.readLine());
	    				
	    				// User's password
	    				System.out.print("Please enter User's password: ");
	    				String userPassword = br.readLine();
	    				Customer customer = (Customer) DatabaseSelectHelper.getUserDetails(userId);
	    				teller.setCurrentCustomer(customer);
	    				
	    				teller.authenticateCurrentCustomer(userPassword);
	    			} else if(condition == 2) {
	    				// User's name
	    				System.out.print("Please enter User's name: ");
	    				String name = br.readLine();
	    				// User's age
	    				System.out.print("Please enter User's age: ");
	    				int age = Integer.parseInt(br.readLine());
	    				// User's address
	    				System.out.print("Please enter User's address: ");
	    				String address = br.readLine();
	    				// User's password
	    				System.out.print("Please enter User's password: ");
	    				String userPassword = br.readLine();
	    				
	    				teller.makeNewUser(name, age, address, userPassword);
	    			} else if(condition == 3) {
	    				System.out.print("Please enter name of account owner: ");
	    				String name = br.readLine();
	    				
	    				System.out.println("1. Chequing Account\n2. Savings Account\n3. Tfsa Account");
	    				int type = Integer.parseInt(br.readLine());
	    				
	    				System.out.print("Please enter the starting balance of the account: ");
	    				String amount = br.readLine();
	    				BigDecimal balance = new BigDecimal(amount); 
	    				 
	    				
	    				System.out.println(teller.makeNewAccount(name, balance, type));
	    			} else if(condition == 4) {
	    				System.out.println("Please enter the account type (ex. CHEQUING): ");
	    				String name = br.readLine();
	    				
	    				System.out.print("Please enter the account id: ");
	    				int accountId = Integer.parseInt(br.readLine());
	    				
	    				System.out.print("Please enter the interest rate of the account: ");
	    				String rate = br.readLine();
	    				BigDecimal interest = new BigDecimal(rate);
	    				DatabaseInsertHelper.insertAccountType(name, interest);
	    				
	    				teller.giveInterest(accountId);
	    			} else if(condition == 5) {
	    				System.out.print("Please enter the account id: ");
	    				int accountId = Integer.parseInt(br.readLine());
	    				
	    				System.out.print("Please enter the amount to be withdrawaled: ");
	    				int am = Integer.parseInt(br.readLine());
	    				BigDecimal amount = new BigDecimal(am);
	    				
	    				teller.makeWithdrawal(amount, accountId);
	    			} else if(condition == 6) {
	    				System.out.print("Please enter the account id: ");
	    				int accountId = Integer.parseInt(br.readLine());
	    
	    				teller.checkBalance(accountId);
	    			} else if(condition == 7) {
	    				connection.close();
	    			} else if(condition == 8) {
	    				teller.deAuthenticateCustomer();
	    			}
	    			
	    		} while (condition != 9);
	    		
	      //If the user entered 2
	    	} else if (input == 2) {
	      /*
	       * TODO create a context menu for the ATM Interface
	       * Prompt the user for their id and password
	       * Attempt to authenticate them
	       * If the authentication fails, repeat
	       * If they get authenticated, give them this menu:
	       * 1. List Accounts and balances (list all accounts and their balances)
	       * 2. Make Deposit
	       * 3. Check balance
	       * 4. Make withdrawal
	       * 5. Exit
	       * 
	       * For each of these, loop through and continue prompting for the information needed
	       * Continue showing the context menu, until the user gives a 5 as input.
	       */
	    		System.out.print("Please enter valid id: ");
	    		int id = Integer.parseInt(br.readLine());
	    		
	    		System.out.print("Please enter valid password: ");
	    		String userPassword = br.readLine();
	    		ATM atm = new ATM(id, userPassword);
	    		int tries = 0;
	    		while(atm.authenticate(id, userPassword) == false && tries < 5) {
	        		System.out.print("Please enter valid id: ");
	        		id = Integer.parseInt(br.readLine());
	        		
	        		System.out.print("Please enter valid password: ");
	        		userPassword = br.readLine();
	    		}
	    		if(tries >= 5) {
	    			System.out.println("Sorry, you ran out of attempts to access this account.");
	    		} else {
	    			int choice;
	    			do {
	        			System.out.println("1. List accounts and balances");
	        			System.out.println("2. Make deposit");
	        			System.out.println("3. Check balance");
	        			System.out.println("4. Make withdrawal");
	        			System.out.println("5. Exit");
	        			choice = Integer.parseInt(br.readLine());
	        			
	        			if(choice == 1) {
	        				List<Account> accounts = atm.listAccounts();
	        				for(int i = 0; i < accounts.size(); i++) {
	        					System.out.println(accounts.get(i).getName() + " with balance of " + accounts.get(i).getBalance());
	        				}
	        			} else if(choice == 2) {
	        				System.out.print("Please enter the amount to deposit: ");
	        				int am = Integer.parseInt(br.readLine());
	        				BigDecimal amount = new BigDecimal(am);
	        				
	        				System.out.println(atm.makeDeposit(amount, id));
	        			} else if(choice == 3) {
	        				System.out.println(atm.checkBalance(id));
	        			} else if(choice == 4) {
	        				System.out.print("Please enter the amount to withdrawal: ");
	        				int am = Integer.parseInt(br.readLine());
	        				BigDecimal amount = new BigDecimal(am);
	        				
	        				System.out.println(atm.makeWithdrawal(amount, id));
	        			}
	    			} while(choice != 5);
	    		}
	    		
	      //If the user entered 0
	        } else if (input == 0) {
	      /*
	       * TODO Exit condition
	       */
	    	connection.close();
	      //If the user entered anything else:
	        } else {
	      /*
	       * TODO Re-prompt the user
	       */
	        	System.out.println("1 - Teller Interface");
	        	System.out.println("2 - ATM Interface");
	        	System.out.println("0 - Exit");
	        	System.out.println("Enter Selection");
	        	input = Integer.parseInt(br.readLine());
	    	}
    	}
      
      
      
    } catch (Exception e) {
      //TODO Improve this!
      System.out.println("Something went wrong :(");
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Looks like it was closed already :)");
      }
    }
    
  }
}
