package com.iqvia.schedule_message.api;

import com.iqvia.schedule_message.domain.Message;
import com.iqvia.schedule_message.domain.MessageDTO;
import com.iqvia.schedule_message.domain.Status;
import com.iqvia.schedule_message.service.MessageService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@ApiResponses(value = {
        @ApiResponse(code=400, message="This is a bad request. Please follow the API documentation for proper request."),
        @ApiResponse(code=500, message="The server is down. Please make sure that the service are running.")
})
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message")
    public ResponseEntity<String> receive(@Valid@RequestBody MessageDTO messageInfo, BindingResult bindingResult){
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
