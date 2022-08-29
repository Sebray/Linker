package com.dsr.linker.controller;

import com.dsr.linker.dto.ChatChannelDto;
import com.dsr.linker.dto.EstablishedChatChannelDto;
import com.dsr.linker.dto.MessageDto;
import com.dsr.linker.security.JwtUser;
import com.dsr.linker.service.ChatChannelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatChannelController {
    private final ChatChannelService chatChannelService;

    @MessageMapping("/private.chat.{channelId}")
    @SendTo("/topic/private.chat.{channelId}")
    public MessageDto chatMessage(@DestinationVariable String channelId, MessageDto message) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        message.getSender().setId(user.getId());
        return chatChannelService.submitMessage(message);
    }

    @PutMapping(value="/api/private-chat/channel")
    public ResponseEntity<EstablishedChatChannelDto> establishChatChannel(@RequestBody ChatChannelDto chatChannelDto) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(chatChannelDto.getSecondAccId() == null)
            chatChannelDto.setSecondAccId(user.getId());
        else
            chatChannelDto.setFirstAccId(user.getId());

        return new ResponseEntity<>(chatChannelService.establishChatSession(chatChannelDto), HttpStatus.OK);
    }

    @GetMapping("/api/private-chat/channel/{channelUuid}")
    public ResponseEntity<List<MessageDto>> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid) {
        return new ResponseEntity<>(chatChannelService.getExistingChatMessages(channelUuid), HttpStatus.OK);
    }
}
