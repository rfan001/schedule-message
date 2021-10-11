package com.iqvia.schedule_message;

import com.iqvia.schedule_message.domain.Message;
import com.iqvia.schedule_message.domain.MessageRepository;
import com.iqvia.schedule_message.domain.Status;
import com.iqvia.schedule_message.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TestMessageService {

    @InjectMocks
    MessageService messageService;

    @Mock
    MessageRepository messageRepository;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
        List<Message> mockMessages = new ArrayList<>();
        for (long i = 0; i <= 5 ;i++){
            Message m = new Message();
            m.setMessageId(i);
            m.setContent("message body " + Long.valueOf(i));
            m.setStatus(Status.ACTIVE);
            m.setPublishTime(LocalDateTime.now().plusSeconds(1L));
            mockMessages.add(m);
        }
        when(messageRepository.findAllByStatusAndPublishTimeLessThanEqualAndPublishTimeGreaterThan
                (any(Status.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockMessages);
    }

    @Test
    public void getMessageList(){
        List<Message> messages = messageService.list();
        assertEquals(6, messages.size());
        verify(messageRepository, times(1)).findAllByStatusAndPublishTimeLessThanEqualAndPublishTimeGreaterThan(
                any(Status.class), any(LocalDateTime.class), any(LocalDateTime.class));
    }
}