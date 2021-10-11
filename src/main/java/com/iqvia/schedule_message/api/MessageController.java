package com.iqvia.schedule_message.api;

import com.iqvia.schedule_message.domain.Message;
import com.iqvia.schedule_message.domain.MessageInfo;
import com.iqvia.schedule_message.domain.Status;
import com.iqvia.schedule_message.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")

public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message")
    public ResponseEntity<String> receive(@Valid@RequestBody MessageInfo messageInfo, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        Message message = new Message();
        message.setContent(messageInfo.getMessage());
        message.setPublishTime(messageInfo.getSchedule());
        message.setStatus(Status.ACTIVE);
        if(this.messageService.save(message) != null){
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
        }
    }
}
