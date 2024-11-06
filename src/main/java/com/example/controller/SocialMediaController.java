package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Account> postRegister (@RequestBody Account account){
        Account accountCont;
        accountCont = accountService.registerNewUser(account);
        if(accountCont==null)
        {
            //Failed for other reasons
            return ResponseEntity.status(400).header("Content-Type", "application/json").body(account);
        }
        else if(accountCont.getAccountId()==0){
            //Duplicated
            return ResponseEntity.status(409).header("Content-Type", "application/json").body(account);

        }
        else{
            //Success
            return ResponseEntity.status(200).header("Content-Type", "application/json").body(accountCont);

        }

    } 

    @PostMapping("/login")
    public ResponseEntity<Account> postLogin (@RequestBody Account account){
        Account accountCont;
        accountCont = accountService.login(account);
        if(accountCont.getAccountId()==0){
            //Failed
            return ResponseEntity.status(401).header("Content-Type", "application/json").body(account);
        }
        else{
            //Sucessfull
            return ResponseEntity.status(200).header("Content-Type", "application/json").body(accountCont);

        }
    }

    
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessages (@RequestBody Message message){
        Message messageCont;
        messageCont = messageService.createNewMessage(message);
        if(messageCont==null){
            //Failed
            return ResponseEntity.status(400).header("Content-Type", "application/json").body(message);
        }
        else{
            //Sucessfull
            return ResponseEntity.status(200).header("Content-Type", "application/json").body(messageCont);

        }
    }


}
