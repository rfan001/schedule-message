package com.iqvia.schedule_message;

import com.iqvia.schedule_message.service.SendMessageService;
import org.junit.jupiter.api.Test;

import com.iqvia.schedule_message.domain.Message;
import com.iqvia.schedule_message.domain.MessageRepository;
import com.iqvia.schedule_message.service.MessageService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ScheduleMessageApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository repo;

    @MockBean
    public SendMessageService sendMessageService;


    @Before
    public void clearDB(){
        repo.deleteAll();
    }

    @Test
    public void TestPostMessageEndpoint() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String json = "{\n" +
                "    \"message\": \"Test Message1\",\n" +
                "    \"schedule\": \""+ now.plusSeconds(2).format(formatter) +"\"\n" +
                "}";
        this.mockMvc.perform(post("/api/message")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        json = "{\n" +
                "    \"message\": \"Test Message2\",\n" +
                "    \"schedule\": \""+ now.plusSeconds(3).format(formatter) +"\"\n" +
                "}";
        this.mockMvc.perform(post("/api/message")
                        .content(json.getBytes())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        System.out.println(LocalDateTime.now());
        Thread.sleep(1900);
        System.out.println(LocalDateTime.now());

        //only return current second's scheduled message
        //indirectly approve the service log matches the expectation
        List<Message> list = messageService.list();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("Test Message1", list.get(0).getContent());

    }

}

