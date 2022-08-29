package com.dsr.linker.service;

import com.dsr.linker.dto.ChatChannelDto;
import com.dsr.linker.dto.EstablishedChatChannelDto;
import com.dsr.linker.dto.MessageDto;

import java.util.List;

public interface ChatChannelService {
    MessageDto submitMessage(MessageDto messageDto);
    List<MessageDto> getExistingChatMessages(String channelUuid);
    EstablishedChatChannelDto establishChatSession(ChatChannelDto chatChannelDto);
}
