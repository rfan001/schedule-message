package com.iqvia.schedule_message.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iqvia.schedule_message.domain.Message;
import com.iqvia.schedule_message.domain.MessageRepository;
import com.iqvia.schedule_message.domain.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        Specification<Message> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            list.add(criteriaBuilder.lessThanOrEqualTo(root.get("publishTime"),now));
            list.add(criteriaBuilder.equal(root.get("status"), Status.ACTIVE));
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        resultList = this.messageRepository.findAll(specification);
        return resultList;
    }
    public Message changeStatus(Long id){
        Message message = messageRepository.getById(id);
        message.setStatus(Status.DONE);
        return messageRepository.save(message);
    }
}
