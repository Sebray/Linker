package com.dsr.linker.serviceImpl;

import com.dsr.linker.dto.ChatChannelDto;
import com.dsr.linker.dto.EstablishedChatChannelDto;
import com.dsr.linker.dto.MessageDto;
import com.dsr.linker.entity.Account;
import com.dsr.linker.entity.ChatChannel;
import com.dsr.linker.entity.Message;
import com.dsr.linker.entity.MessageStatus;
import com.dsr.linker.exception.ResourceNotAllowedException;
import com.dsr.linker.exception.ResourceNotFoundException;
import com.dsr.linker.mapper.ChatChannelMapper;
import com.dsr.linker.mapper.MessageMapper;
import com.dsr.linker.repository.AccountRepository;
import com.dsr.linker.repository.ChatChannelRepository;
import com.dsr.linker.repository.MessageRepository;
import com.dsr.linker.repository.MessageStatusRepository;
import com.dsr.linker.service.ChatChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatChannelServiceImpl implements ChatChannelService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final ChatChannelRepository chatChannelRepository;
    private final MessageStatusRepository messageStatusRepository;
    private final MessageMapper messageMapper;
    private final ChatChannelMapper chatChannelMapper;

    @Override
    public MessageDto submitMessage(MessageDto messageDto) {
        if (messageDto.getText().trim().equals(""))
            throw new ResourceNotAllowedException("No text");

        Account sender = accountRepository.findById(messageDto.getSender().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No user with id " + messageDto.getSender().getId()));
        Account receiver = accountRepository.findById(messageDto.getReceiver().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No user with id " + messageDto.getReceiver().getId()));
        MessageStatus messageStatus = messageStatusRepository.findByName("Отправлено");
        Message repliedMessage = messageRepository.findById(messageDto.getRepliedMessage().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No message with id " + messageDto.getRepliedMessage().getId()));

        Message message = new Message(messageDto.getId(), messageDto.getText(),
                messageDto.getSendingDate(), sender, receiver, messageStatus, repliedMessage);

        return messageMapper.toMessageDto(messageRepository.save(message));
    }

    public EstablishedChatChannelDto establishChatSession(ChatChannelDto chatChannelDto) {
        Account first = accountRepository.findById(chatChannelDto.getFirstAccId())
                .orElseThrow(() -> new ResourceNotFoundException("No user with id " + chatChannelDto.getFirstAccId()));
        Account second = accountRepository.findById(chatChannelDto.getSecondAccId())
                .orElseThrow(() -> new ResourceNotFoundException("No user with id " + chatChannelDto.getSecondAccId()));

        ChatChannel chatChannel = chatChannelRepository.getChatByIds(chatChannelDto.getFirstAccId(),
                chatChannelDto.getSecondAccId())
                .orElse(chatChannelRepository.save(new ChatChannel(UUID.randomUUID().toString(), first, second)));

        return chatChannelMapper.toEstablishedChatChannelDto(chatChannel);
    }


    public List<MessageDto> getExistingChatMessages(String channelUuid) {
        ChatChannel channel = chatChannelRepository.getChatChannelByUuid(channelUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Not found chat channel with uuid " + channelUuid));
        return messageMapper.toMessageDto(messageRepository.
                getMessages(channel.getFirstAcc().getId(), channel.getSecondAcc().getId()));
    }

}
