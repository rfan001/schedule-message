package com.iqvia.schedule_message.service;

import com.iqvia.schedule_message.domain.Message;
import com.iqvia.schedule_message.domain.MessageRepository;
import com.iqvia.schedule_message.domain.Status;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    public Message save(Message message){
        return messageRepository.save(message);
    }
    public List<Message> list(){
        List<Message> resultList;
        LocalDateTime now = LocalDateTime.now();
        resultList = this.messageRepository.
                findAllByStatusAndPublishTimeLessThanEqualAndPublishTimeGreaterThan(
                        Status.ACTIVE,
                        now.plusSeconds(1L),
                        now
                );
        return resultList;
    }
    public List<Message> firstList(){
        List<Message> resultList;
        LocalDateTime now = LocalDateTime.now();
        resultList = this.messageRepository.
                findAllByStatusAndPublishTimeLessThanEqual(
                        Status.ACTIVE,
                        now
                );
        return resultList;
    }
    public Message changeStatus(Long id){
        Message message = messageRepository.getById(id);
        message.setStatus(Status.DONE);
        return messageRepository.save(message);
    }
}
