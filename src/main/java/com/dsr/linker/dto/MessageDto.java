package com.dsr.linker.dto;

import com.dsr.linker.dto.account.AccountForChatDto;
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
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private Date sendingDate;

    @JsonProperty(value = "sender")
    private AccountForChatDto sender;

    @JsonProperty(value = "receiver")
    private AccountForChatDto receiver;

    @JsonProperty(value = "messageStatusId")
    private Long messageStatus;

    @JsonProperty(value = "repliedMessage")
    private RepliedMessageDto repliedMessage;
}
