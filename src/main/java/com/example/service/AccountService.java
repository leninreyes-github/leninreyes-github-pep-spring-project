package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerNewUser(Account account) //throws DuplicateRowException
    {
        if(account.getUsername().length()>0&&account.getPassword().length()>=4){
            Optional<Account> accountOpt;
            accountOpt = accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword());
            if (accountOpt.isPresent()) {
                //throw new DuplicateRowException("Duplicate row found");
                account.setAccountId(0); //future review
                return account;//accountOpt.get();
            } else {
                account = accountRepository.save(account);
                return account;   
            }
        }
        else
        {
            return null;//future reviw
        }
    }

    public Account login(Account account){
        Optional<Account> accountOpt = accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword());
        if (accountOpt.isPresent()) {
            return accountOpt.get();
        }
        else{
            account.setAccountId(0); //future review
            return account;
        }
    }



    /*
    public class DuplicateRowException extends RuntimeException {

        public DuplicateRowException(String message) {
            super(message);
        }
    }*/



}
