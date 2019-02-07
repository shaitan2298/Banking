package com.cg.banking.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;
import com.cg.banking.daoservices.AccountDAO;
import com.cg.banking.daoservices.AccountDAOImpl;
import com.cg.banking.exceptions.AccountBlockedException;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServiceDownException;
import com.cg.banking.exceptions.InsufficientAmountException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.exceptions.InvalidPinNumberException;

import cg.com.banking.util.BankingDBUtil;

public class BankingServicesImpl implements BankingServices
{
	private AccountDAO accountDAO = new AccountDAOImpl();
	int count = 1;
	@Override
	public Account openAccount(String accountType, float initBalance)
			throws InvalidAmountException, InvalidAccountTypeException, BankingServiceDownException 
	{
		if(initBalance < 1000.0f) 
		{
			throw new  InvalidAmountException("Minimum amount should be above than Rs 1000");
		}
		
		if(accountType.equals("Savings") || accountType.equals("Current")) {
		Account account = new Account(accountType, initBalance);
		account.setAccountStatus("Active");
		account.transactions = new HashMap<>();
		account = accountDAO.save(account);
		
		return account;
		}
		else {
		
			 throw new InvalidAccountTypeException("Invalid Account Type Exception! Please try Again");
		}
	}

	@Override
	public float depositAmount(long accountNo, float amount)
			throws AccountNotFoundException, BankingServiceDownException, AccountBlockedException 
	{
		Account account = accountDAO.findOne(accountNo);
		
		  if(account == null) 
			  throw new AccountNotFoundException("Account Not Found!"+ accountNo); 
		 
		float currentAmount =  account.getAccountBalance() + amount;
		
		Transaction transaction = new Transaction();
		Integer transactionId = BankingDBUtil.getTRANSACTION_ID();
		int tId = transactionId;
		transaction.setTransactionId(tId);
		transaction.setTransactionType(BankingDBUtil.getDEPOSIT_STATUS());
		transaction.setAmount(amount);
		account.transactions.put(BankingDBUtil.getTRANSACTION_ID(),transaction);
		return currentAmount;
	}

	@Override
	public float withdrawAmount(long accountNo, float amount, long pinNumber) throws InsufficientAmountException,
			AccountNotFoundException, InvalidPinNumberException, BankingServiceDownException, AccountBlockedException {
		
		
		Account account = accountDAO.findOne(accountNo);
		float newAmount =account.getAccountBalance();
		if(account == null)
			throw new AccountNotFoundException("Account Not Found!" + accountNo);

		getAccountDetails(accountNo);
		if(pinNumber == account.getPinNumber())
		{
			newAmount = account.getAccountBalance() - amount;
			account.setAccountBalance(newAmount);
			Transaction transaction = new Transaction();
			Integer transactionId = BankingDBUtil.getTRANSACTION_ID();
			int tId = transactionId;
			transaction.setTransactionId(tId);
			transaction.setTransactionType(BankingDBUtil.getWITHDRAW_STATUS());
			transaction.setAmount(newAmount);		
			account.transactions.put(BankingDBUtil.getTRANSACTION_ID(),transaction);
			return newAmount;
		}
			else if(pinNumber != account.getPinNumber())
		{
			count++;
			System.out.println("Enter valid pin number!");
			if(count % 4 == 0)
			{
				account.setAccountStatus("Blocked");
				throw new AccountBlockedException("Sorry Your Account Has Been Blocked!");
			}
		
		float currentAmount = account.getAccountBalance();
		if(amount > currentAmount)
		{
			throw new InsufficientAmountException("Insufficient Amount in the Account! " + accountNo);
		}
		}
																			
	return newAmount;
	}
	@Override
	public boolean fundTransfer(long accountNoTo, long accountNoFrom, float transferAmount, long pinNumber)
			throws InsufficientAmountException, AccountNotFoundException, InvalidPinNumberException,
			BankingServiceDownException, AccountBlockedException {
		Account account = accountDAO.findOne(accountNoFrom);
		withdrawAmount(accountNoFrom, transferAmount,pinNumber);
		/*
		 * int count = 0; if(pinNumber == account.getPinNumber()) { float deductedAmount
		 * = account.getAccountBalance() - transferAmount; if(deductedAmount <
		 * account.getAccountBalance()) throw new InsufficientAmountException();
		 * account.setAccountBalance(deductedAmount);
		 * 
		 * } else if(pinNumber != account.getPinNumber()) {
		 * 
		 * count++; if(count < 3) { throw new
		 * InvalidPinNumberException("Invalid Pin Number!!"); } else if(count % 3 ==0) {
		 * throw new
		 * AccountBlockedException("3 Attempts has been made, Account has been Blocked!"
		 * ); } }
		 */
			
			
		getAccountDetails(accountNoTo);
		account = accountDAO.findOne(accountNoTo);
		depositAmount(accountNoTo,transferAmount);
		getAccountDetails(accountNoFrom);
		/*
		 * float addedAmount = account.getAccountBalance() + transferAmount;
		 * account.setAccountBalance(addedAmount);
		 */
		return true;
	}

	@Override
	public Account getAccountDetails(long accountNo) throws AccountNotFoundException, BankingServiceDownException {
		if(accountDAO.findOne(accountNo)==null)
		{
			throw new AccountNotFoundException("Account Not Found");
		}
		return accountDAO.findOne(accountNo);
		
	}

	@Override
	public List<Account> getAllAccountDetails() throws BankingServiceDownException {
		
		return accountDAO.findAll();
	}

	@Override
	public List<Transaction> getAccountAllTransaction(long accountNo)
			throws AccountNotFoundException, BankingServiceDownException {
		Account account = accountDAO.findOne(accountNo);
		if(account == null)
            throw new AccountNotFoundException("Account Not Found");
		ArrayList<Transaction> arrayList = new ArrayList<>(account.transactions.values());
		if(arrayList.size()==0)
			throw new BankingServiceDownException("No transactions available!");
		return arrayList;
	}

	@Override
	public String accountStatus(long accountNo)
			throws BankingServiceDownException, AccountNotFoundException, AccountBlockedException {
		Account account = accountDAO.findOne(accountNo);
		/*
		 * if(account == null) throw new AccountNotFoundException("Account not found!");
		 */
		return account.getAccountStatus();
	}

}
