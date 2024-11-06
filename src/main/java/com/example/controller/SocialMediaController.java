package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Account> register (@RequestBody Account account){
        Account accountCont;
        accountCont = accountService.registerNewUser(account);
        if(accountCont==null)
        {
            return ResponseEntity.status(400).header("Content-Type", "application/json").body(account);
        }
        else if(accountCont.getAccountId()==0){
            return ResponseEntity.status(409).header("Content-Type", "application/json").body(account);

        }
        else{
            return ResponseEntity.status(200).header("Content-Type", "application/json").body(accountCont);

        }

    } 

    @PostMapping("/login")
    public ResponseEntity<Account> login (@RequestBody Account account){
        Account accountCont;
        accountCont = accountService.login(account);
        if(accountCont.getAccountId()==0){
            return ResponseEntity.status(401).header("Content-Type", "application/json").body(account);

        }
        else{
            return ResponseEntity.status(200).header("Content-Type", "application/json").body(accountCont);

        }
    }


}
