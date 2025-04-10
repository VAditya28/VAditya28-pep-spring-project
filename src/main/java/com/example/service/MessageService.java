package com.example.service;
import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.Optional;
import com.example.repository.*;


@Service
public class MessageService {
    
    private MessageRepository messagerepository;
   
    @Autowired
    public MessageService(MessageRepository messagerepository){
        this.messagerepository= messagerepository;
    }

    //Returns all the messages
    public List<Message> getallmessages(){
            return messagerepository.findAll();
        
    }

    public Message getmessagebyid(int messageid){
        Optional<Message> optionalmessage= messagerepository.findById(messageid);
        if(optionalmessage.isEmpty()){
            return null;
        }
        return optionalmessage.get();
    }

    public List<Message> getusermessages(int postedby){

        return messagerepository.findByPostedBy(postedby);
        
    }

    public Message createmessage(Message message){
        boolean userexists= messagerepository.existsById(message.getPostedBy());
        if(message.getMessageText().isBlank()|| message.getMessageText().length()>255 || userexists==false){
            return null;
        }
        return messagerepository.save(message);
    }

    public int deleteMessage (int messageid){
        boolean userexists = messagerepository.existsById(messageid);
        if(userexists==true){
            messagerepository.deleteById(messageid);
            return 1;
        }
        return 0;
        
    }

    public int updatemessage (int messageid, Message replacement){
        Optional<Message> optionalmessage= messagerepository.findById(messageid);
        if(replacement.getMessageText().isBlank()==false && replacement.getMessageText().length()<255 && optionalmessage.isPresent()){
         Message message= optionalmessage.get();
         message.setMessageText(replacement.getMessageText());
         messagerepository.save(message);
         return 1;

        }
        return 0;
        
        
    }

   
    

}
