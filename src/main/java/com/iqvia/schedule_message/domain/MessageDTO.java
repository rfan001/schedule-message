package com.iqvia.schedule_message.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
public class MessageDTO {
    @NotBlank(message = "The message cannot be blank")
    @ApiModelProperty(notes = "Scheduled Message", example = "Test Test!")
    private String message;

    @NotNull(message = "The schedule cannot be null")
    @Future(message = "Schedule must in future")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "Schedule Time", example = "2021-10-11 15:45:00")
    private LocalDateTime schedule;
}
