package com.dsr.linker.mapper;

import com.dsr.linker.dto.EstablishedChatChannelDto;
import com.dsr.linker.dto.account.AccountForChatDto;
import com.dsr.linker.entity.ChatChannel;
import org.springframework.stereotype.Component;

@Component
public class ChatChannelMapper {
    public EstablishedChatChannelDto toEstablishedChatChannelDto(ChatChannel channel){
        return new EstablishedChatChannelDto(channel.getUuid(),
                new AccountForChatDto(channel.getFirstAcc().getId(),channel.getFirstAcc().getFirstName(),
                        channel.getFirstAcc().getLastName()),
                new AccountForChatDto(channel.getSecondAcc().getId(),channel.getSecondAcc().getFirstName(),
                        channel.getSecondAcc().getLastName()));
    }
}
