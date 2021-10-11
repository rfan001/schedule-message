package com.iqvia.schedule_message.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "message")
/**
 * Message Entity
 */
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;  // ACTIVE and DONE

    @Column(name = "publish_time")
    private LocalDateTime publishTime; // format "yyyy-MM-dd HH:mm:ss"

    @Column(name = "created_time", updatable=false)
    @CreationTimestamp
    private Date createdTime;
}
