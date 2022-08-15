package com.dsr.linker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "text")
    private String text;

    @JsonProperty(value = "sendingDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sendingDate;

    @JsonProperty(value = "senderId")
    private Long sender;

    @JsonProperty(value = "receiverId")
    private Long receiver;

    @JsonProperty(value = "messageStatusId")
    private Long messageStatus;

    @JsonProperty(value = "repliedMessageId")
    private Long repliedMessage;
}
