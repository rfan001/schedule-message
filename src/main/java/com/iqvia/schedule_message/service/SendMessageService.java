package com.iqvia.schedule_message.service;

import com.iqvia.schedule_message.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class SendMessageService {
    private final MessageService messageService;
    private static final Logger log = LoggerFactory.getLogger(SendMessageService.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SendMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        List<Message> messages = this.messageService.list();
        for (Message message: messages) {
            log.info("The time is now {}",message.getPublishTime().format(formatter));
            log.info("message: {}", message.getContent());
            this.messageService.changeStatus(message.getMessageId());
        }
    }
}
