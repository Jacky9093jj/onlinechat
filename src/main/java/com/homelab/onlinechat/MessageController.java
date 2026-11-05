package com.homelab.onlinechat;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageInput input) {

        User sender = userRepository.findByUserId(input.getSenderId()).orElse(null);
        User receiver = userRepository.findByUserId(input.getReceiverId()).orElse(null);

        if (sender == null || receiver == null) {
            return ResponseEntity.badRequest().body("No such sender/receiver");
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(input.getContent());

        messageRepository.save(message);
        return ResponseEntity.ok("Message sent！");
    }

    @GetMapping("/history")
    public List<Message> getChatHistory(@RequestParam String user1, @RequestParam String user2) {
        return messageRepository.findChatHistory(user1, user2);
    }
}


// @Getter add back after finish this part of code
class MessageInput {
    private String senderId;
    private String receiverId;
    private String content;


    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public String getReceiverId() { return receiverId; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
