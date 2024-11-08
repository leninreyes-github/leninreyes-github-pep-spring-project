package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
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

    private AccountService accountService;
    private MessageService messageService;

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

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        List<Message> listMessageCont;
        listMessageCont = messageService.getMessageList();
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(listMessageCont);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Optional<Message>> getMessagesById(@PathVariable Integer messageId){
        Optional<Message> messageFound;
        messageFound = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(messageFound);
    }
    
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessagesById(@PathVariable Integer messageId){
        System.out.println("LR Si entra a Controller Delete");
        Integer messageDel;
        messageDel = messageService.deleteMessageById(messageId);
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(messageDel);
    }    

    
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessagesById(@PathVariable Integer messageId, @RequestBody Message message){
        Integer messageUpd;
        Integer httpStatus;
          messageUpd = messageService.updateMessageById(messageId,message.getMessageText());
        if (messageUpd==null){
            httpStatus = 400;
        }
        else{
            httpStatus = 200;
        }   
        return ResponseEntity.status(400).header("Content-Type", "application/json").body(messageUpd);
  
    }

    
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountid(@PathVariable Integer accountId){
        List<Message> messagesFound;
        Integer httpStatus;
        messagesFound = messageService.getMessageByAccountId(accountId);
        if(messagesFound.isEmpty())
        {
            httpStatus = 400;
        }
        else{
            httpStatus = 200;
        }
        return ResponseEntity.status(httpStatus).header("Content-Type", "application/json").body(messagesFound);      
    }
    
    

}
