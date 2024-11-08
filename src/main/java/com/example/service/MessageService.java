package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createNewMessage(Message message) 
    {
        if(message.getMessageText().length()<=255&&message.getMessageText().length()>0){
            Optional<Account> accountOpt;
            accountOpt = accountRepository.findById(message.getPostedBy());
            if (accountOpt.isPresent()) {
                message = messageRepository.save(message);
                return message;   
            } else {
                return null; 
            }
        }
        else{
            return null;
        }
    }

    public List<Message> getMessageList(){
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId){
        Optional<Message> messageTemp;
        messageTemp = messageRepository.findById(messageId);
        System.out.println("messageTemp"+messageTemp);
        if(messageTemp.isPresent()){
            return messageTemp;
        }
        else{
            return null;
        }
    }

    public Integer deleteMessageById(Integer messageId){
        Optional<Message> messageTemp;
        messageTemp = messageRepository.findById(messageId);
        System.out.println("messageTemp"+messageTemp);
        if(messageTemp.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        else{
            return null;
        }

    }

    
    public Integer updateMessageById(Integer messageId, String messageText){

        if(messageText.length()<=255&&messageText.length()>0){
            Optional<Message> messageTemp;
            messageTemp = messageRepository.findById(messageId);
            if (messageTemp.isPresent()) {
                Message message = messageTemp.get();
                message.setMessageText(messageText);
                messageRepository.save(message);
                return 1;   
            } else {
                return null; 
            }
        }
        else{
            return null;
        }

    }
  
    public List<Message> getMessageByAccountId(Integer postedBy){
        return messageRepository.findAllByPostedby(postedBy);
    }

}

