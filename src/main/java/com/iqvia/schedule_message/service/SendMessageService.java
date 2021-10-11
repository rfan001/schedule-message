package com.iqvia.schedule_message.service;

import com.iqvia.schedule_message.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component

public class SendMessageService {
    private final MessageService messageService;
    private static final Logger log = LoggerFactory.getLogger(SendMessageService.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SendMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    private boolean firstRun = true;

    @Scheduled(fixedRate = 1000)
    public void sendMessage() {

        List<Message> messages;
        if (firstRun) {
            messages = this.messageService.firstList();
            firstRun = false;
        } else {
            messages = this.messageService.list();
        }
        for (Message message : messages) {
            log.info("Time: {}", message.getPublishTime().format(formatter));
            log.info("Message: {}", message.getContent());
            this.messageService.changeStatus(message.getMessageId()); //TODO: update all ids together
        }
    }
}
