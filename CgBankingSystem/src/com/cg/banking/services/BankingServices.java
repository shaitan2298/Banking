package com.cg.banking.services;
import com.cg.banking.exceptions.*;

import java.util.List;

import com.cg.banking.beans.Account;
import com.cg.banking.beans.Transaction;
import com.cg.banking.exceptions.AccountBlockedException;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServiceDownException;
import com.cg.banking.exceptions.InsufficientAmountException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.exceptions.InvalidPinNumberException;

public interface BankingServices 
{
      Account openAccount(String accountType, float initBalance) throws InvalidAmountException, InvalidAccountTypeException, BankingServiceDownException;
     
      float depositAmount(long accountNo, float amount) throws AccountNotFoundException, BankingServiceDownException, AccountBlockedException;
      
      float withdrawAmount(long accountNo, float amount, long pinNumber) throws InsufficientAmountException, AccountNotFoundException,
      InvalidPinNumberException, BankingServiceDownException, AccountBlockedException;
      
      boolean fundTransfer(long accountNoTo, long accountNoFrom, float transferAmount,long pinNumber) throws InsufficientAmountException,
      AccountNotFoundException, InvalidPinNumberException, BankingServiceDownException, AccountBlockedException;
      
      Account getAccountDetails(long accountNo) throws AccountNotFoundException, BankingServiceDownException;
      
      List<Account> getAllAccountDetails() throws BankingServiceDownException;
      
      List<Transaction> getAccountAllTransaction(long accountNo) throws AccountNotFoundException, BankingServiceDownException;
      
      public String accountStatus(long accountNo) throws BankingServiceDownException, AccountNotFoundException, AccountBlockedException;
      
}
