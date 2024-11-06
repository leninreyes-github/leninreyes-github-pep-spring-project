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
        Optional<Account> accountOpt = accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword());
 
        if (accountOpt.isPresent()) {
            //throw new DuplicateRowException("Duplicate row found");
            return account;//accountOpt.get();
        } else {
                account = accountRepository.save(account);
                return account;          
        }
 
    }

    public Account login(Account account){
        Optional<Account> accountOpt = accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword());
        System.out.println("AccountOpt:"+accountOpt.isPresent());
        if (accountOpt.isPresent()) {
            System.out.println("esta presente");
            return accountOpt.get();
        }
        else{
            
            System.out.println("no esta presente");
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
