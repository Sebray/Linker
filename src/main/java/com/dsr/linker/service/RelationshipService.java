package com.dsr.linker.service;

import com.dsr.linker.dto.account.AccountItemDto;
import com.dsr.linker.dto.PageDto;
import com.dsr.linker.dto.RelationshipDto;
import org.springframework.data.domain.Page;

public interface RelationshipService {
    RelationshipDto addFriend(Long senderId, Long friendId);
    void deleteFriend(Long senderId, Long friendId);
    RelationshipDto getRelationship(Long senderId, Long friendId);
    Page<AccountItemDto> getFriends(Long senderId, PageDto pageDto);
}
