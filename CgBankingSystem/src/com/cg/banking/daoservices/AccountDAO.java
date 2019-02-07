package com.cg.banking.daoservices;

import java.util.List;

import com.cg.banking.beans.Account;

public interface AccountDAO 
{
    Account save(Account account);
    boolean update(Account account);
    Account findOne(long accountNo);
    List<Account> findAll();
}
