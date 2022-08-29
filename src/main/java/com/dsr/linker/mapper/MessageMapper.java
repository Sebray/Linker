package com.dsr.linker.mapper;

import com.dsr.linker.dto.MessageDto;
import com.dsr.linker.dto.RepliedMessageDto;
import com.dsr.linker.dto.account.AccountForChatDto;
import com.dsr.linker.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {
    public MessageDto toMessageDto(Message message){
        return new MessageDto(message.getId(), message.getText(), message.getSendingDate(),
                new AccountForChatDto(message.getSender().getId(),
                        message.getSender().getFirstName(), message.getSender().getLastName()),
                new AccountForChatDto(message.getReceiver().getId(),
                        message.getReceiver().getFirstName(), message.getReceiver().getLastName()),
                message.getMessageStatus().getId(),
                message.getRepliedMessage()!=null?new RepliedMessageDto(message.getRepliedMessage().getId(),
                        message.getRepliedMessage().getText(), message.getRepliedMessage().getSender().getLastName()):null);
    }

    public List<MessageDto> toMessageDto(List<Message> messages) {
        return messages.stream().map(this::toMessageDto).collect(Collectors.toList());
    }
}
