package com.iqvia.schedule_message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduleMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleMessageApplication.class, args);
    }

}
