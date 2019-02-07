package com.cg.banking.client;

import java.util.Scanner;

import com.cg.banking.beans.Account;
import com.cg.banking.exceptions.AccountBlockedException;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServiceDownException;
import com.cg.banking.exceptions.InsufficientAmountException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.exceptions.InvalidPinNumberException;
import com.cg.banking.services.BankingServices;
import com.cg.banking.services.BankingServicesImpl;

public class MainClass
{
	static Scanner sc = new Scanner(System.in);
	static BankingServices services = new BankingServicesImpl();
    public static void main(String args[]) throws InvalidAmountException, InvalidAccountTypeException, InvalidPinNumberException,
    BankingServiceDownException,InsufficientAmountException, InsufficientAmountException, AccountBlockedException, AccountNotFoundException
    {
    	mainScreen();
    	int choice = sc.nextInt();
    	selectOption(choice);
    }
    public static void selectOption(int choice) throws InvalidAmountException, InvalidAccountTypeException, InvalidPinNumberException,
    BankingServiceDownException,InsufficientAmountException, InsufficientAmountException, AccountBlockedException, AccountNotFoundException
    {
    	switch(choice)
    	{
    	case 1:try { 
    		System.out.println("Enter the type of account you want to open:-  1. Savings    \t    2. Current ");
    		String accountType = sc.next();
    		System.out.println("Enter client balance: ");
    		float initBalance = sc.nextFloat();
    		Account account = services.openAccount(accountType, initBalance);
    		System.out.println("**********************ACCOUNT CREATED************************");
    		System.out.println(account);
    	}
    	catch(InvalidAmountException | InvalidAccountTypeException | BankingServiceDownException e)
    	{
    		e.printStackTrace();
    	}
    	break;
    	case 2: System.out.println("Enter the account number: ");
    	               long accountNo = sc.nextLong();
    	               System.out.println(services.getAccountDetails(accountNo));
    	               break;
    	case 3: System.out.println(services.getAllAccountDetails());
    	               break;
    	case 4:try { 
    		System.out.println("Enter the amount to be deposited: ");
    		float depAmount = sc.nextFloat();
    		System.out.println("Enter the account number: ");
    		long depAccountNo = sc.nextLong();
    		System.out.println("The updated amount is: "); 
    		System.out.println(services.depositAmount(depAccountNo, depAmount));
    	}
    	catch(AccountNotFoundException | BankingServiceDownException | AccountBlockedException e)
    	{
    		e.printStackTrace();
    	}
    	break;
    	case 5:try { 
    		System.out.println("Enter the amount to be withdraw: ");
    		float withAmount = sc.nextFloat();
    		System.out.println("Enter the account number: ");
    		long withAccountNo = sc.nextLong();
    		System.out.println("Enter the pin number: ");
    		long pinNumber1 = sc.nextInt();
    		System.out.println("The updated amount is: ");

    		System.out.println(services.withdrawAmount(withAccountNo, withAmount, pinNumber1));
    	}
    	catch(InsufficientAmountException | AccountNotFoundException | InvalidPinNumberException | BankingServiceDownException | AccountBlockedException e)
    	{
    		e.printStackTrace();
    	}
    	break;
    	case 6:try { 
    		System.out.println("Enter the to account number: ");
    		long toAccountNo = sc.nextLong();
    		System.out.println("Enter the from account number: ");
    		long fromAccountNo = sc.nextLong();
    		System.out.println("Enter the amount to be transferred: ");
    		float transferAmount = sc.nextFloat();
    		System.out.println("Enter the pin Number: ");
    		long pinNumber2 = sc.nextLong();
    		System.out.println(services.fundTransfer(toAccountNo, fromAccountNo, transferAmount, pinNumber2));
    	}
    	catch(InsufficientAmountException | AccountNotFoundException |  InvalidPinNumberException |
    			BankingServiceDownException | AccountBlockedException e)
    	{
    		e.printStackTrace();
    	}
    	break;
    	default: System.out.println("Invalid Choice! Please Try Again");            

    	}
    	System.out.println("What do you want to do now?");
    	System.out.println("1. Continue \n 2. Exit");
    	int select = sc.nextInt();
    	if(select == 2)
    		System.exit(0);
    	main(null);
    }
    public static void mainScreen()
    {
    	System.out.println("\t \t Welcome to My Bank \t \t");
    	System.out.println("Enter your choice:");
    	System.out.println("1. Create an account");
    	System.out.println("2. Get your account details");
    	System.out.println("3. Get all account details");
    	System.out.println("4. Deposit Amount");
    	System.out.println("5. Withdraw Amount");
    	System.out.println("6. Fund Transfer");
    }
}
