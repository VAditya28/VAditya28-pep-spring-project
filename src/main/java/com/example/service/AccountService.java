package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.*;

@Service
public class AccountService {

    
    private AccountRepository accountRepository;
   
    @Autowired
    public AccountService(AccountRepository accountRepository ){
        this.accountRepository= accountRepository;
    }


    public Account Register(Account accounttoregister){
        Account existingaccount= accountRepository.findByUsername(accounttoregister.getUsername());
        if(existingaccount==null){
            if(!accounttoregister.getUsername().isBlank()&& accounttoregister.getUsername()!=null &&accounttoregister.getPassword()!=null && accounttoregister.getPassword().length()>=4){
                 return accountRepository.save(accounttoregister);
            }
        }
            return null;
    }

    public Account login (Account accounttologin){
        Account existingaccount= accountRepository.findByUsername(accounttologin.getUsername());
           if (existingaccount!=null) {
              if(existingaccount.getUsername().equals(accounttologin.getUsername()) && existingaccount.getPassword().equals(accounttologin.getPassword())){
                return existingaccount;
              }
           }
           return null;
    }
}
