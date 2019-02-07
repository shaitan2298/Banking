package com.cg.banking.daoservices;

import java.util.ArrayList;
import java.util.List;

import com.cg.banking.beans.Account;

import cg.com.banking.util.BankingDBUtil;

public class AccountDAOImpl implements AccountDAO
{
     
	@Override
	public Account save(Account account) {
		account.setAccountNo(BankingDBUtil.getACCOUNT_NO_COUNTER());
		account.setPinNumber(BankingDBUtil.getPIN_NUMBER());
		BankingDBUtil.account.put( account.getAccountNo(), account);
		BankingDBUtil.account.put(account.getPinNumber(),account);
		return account;
	}

	@Override
	public boolean update(Account account) {
		
		return false;
	}

	@Override
	public Account findOne(long accountNo) {
		return BankingDBUtil.account.get(accountNo);
		
	}

	@Override
	public List<Account> findAll() {
		return new ArrayList<Account>(BankingDBUtil.account.values());
		
	}
	

}
