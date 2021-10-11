package com.iqvia.schedule_message.domain;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAll(Specification<Message> var1);
    List<Message> findAllByStatusAndPublishTimeLessThanEqualAndPublishTimeGreaterThan(Status status, LocalDateTime start, LocalDateTime end);
    List<Message> findAllByStatusAndPublishTimeLessThanEqual(Status status, LocalDateTime end);
}
