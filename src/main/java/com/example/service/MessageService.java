package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    public MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createNewMessage(Message message) //throws DuplicateRowException
    {
       return message;
        /* Optional<Account> accountOpt = accountRepository.findByUsernameAndPassword(account.getUsername(),account.getPassword());
        
        
        if (accountOpt.isPresent()) {
            //throw new DuplicateRowException("Duplicate row found");
            account.setAccountId(0); //future review
            return account;//accountOpt.get();
        } else {
            if(account.getUsername().length()>0&&account.getPassword().length()>=4){
                account = accountRepository.save(account);
                return account;   
            }
            else{
                return null;   //future review
            }       
        }
        */
    }    

}
