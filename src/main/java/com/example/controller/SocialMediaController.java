package com.example.controller;

import java.util.*;

import javax.websocket.server.PathParam;

import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/")
public class SocialMediaController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountService accountService;

    
    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService= messageService;
        this.accountService= accountService;    
    }
  
    @GetMapping("/messages")
    public @ResponseBody List<Message> getallmessages(){
        return messageService.getallmessages();
    }

    @GetMapping("/messages/{messageid}")
    public @ResponseBody Message getmessagebyid (@PathVariable int messageid){
        return messageService.getmessagebyid(messageid);
    }
    
  
    @GetMapping("/accounts/{postedby}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getusermessages (@PathVariable int postedby){
        List< Message>usermessages= messageService.getusermessages(postedby);
        return ResponseEntity.status(HttpStatus.OK).body(usermessages);   
    }
    
    
     
     @PostMapping("/messages")
     public @ResponseBody ResponseEntity<Message> createMessage (@RequestBody Message message){
         Message createdmessage= messageService.createmessage(message);
         if(createdmessage!=null){
             return ResponseEntity.status(HttpStatus.OK).body(createdmessage);
         }
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();    
     }
     
 
     
    @DeleteMapping("/messages/{messageid}")
    public  @ResponseBody ResponseEntity<Integer> deletebyID (@PathVariable int messageid){
        int rowsaffected= messageService.deleteMessage(messageid);
        if(rowsaffected!=0){
            return ResponseEntity.ok().body(rowsaffected);
       }
        return ResponseEntity.ok().body(null);
     }

 
     @PatchMapping("/messages/{messageid}")
    public  @ResponseBody ResponseEntity<Integer> updatemessage (@PathVariable int messageid, @RequestBody Message replacement){
        int rowsaffected= messageService.updatemessage(messageid, replacement);
        if(rowsaffected!=0){
            return ResponseEntity.ok().body(rowsaffected);
       }
        return ResponseEntity.badRequest().body(null);       
     }
     
   
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account accounttoregister){    
        Account createdAccount= accountService.Register(accounttoregister);
        if(createdAccount!=null){       
                return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(createdAccount); 
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> Login(@RequestBody Account accounttologin){    
       Account login= accountService.login(accounttologin);   
        if(login!=null){
                return ResponseEntity.status(HttpStatus.OK).body(login);  
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  
    }
}
