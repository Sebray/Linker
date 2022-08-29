package com.dsr.linker.serviceImpl;

import com.dsr.linker.dto.account.AccountItemDto;
import com.dsr.linker.dto.PageDto;
import com.dsr.linker.dto.RelationshipDto;
import com.dsr.linker.entity.Account;
import com.dsr.linker.entity.Relationship;
import com.dsr.linker.entity.RelationshipId;
import com.dsr.linker.entity.RelationshipStatus;
import com.dsr.linker.exception.ResourceNotAllowedException;
import com.dsr.linker.exception.ResourceNotFoundException;
import com.dsr.linker.mapper.AccountMapper;
import com.dsr.linker.mapper.RelationshipMapper;
import com.dsr.linker.repository.AccountRepository;
import com.dsr.linker.repository.RelationshipRepository;
import com.dsr.linker.repository.RelationshipStatusRepository;
import com.dsr.linker.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {
    private final RelationshipRepository relationshipRepository;
    private final AccountRepository accountRepository;
    private final RelationshipStatusRepository relationshipStatusRepository;
    private final RelationshipMapper relationshipMapper;
    private final AccountMapper accountMapper;


    @Override
    public RelationshipDto addFriend(Long senderId, Long friendId) {
        Relationship relationship = relationshipRepository.findByFirstAccAndSecondAcc(senderId, friendId);
        RelationshipStatus relationshipStatus;

        if(relationship == null){
            relationshipStatus = relationshipStatusRepository.findFirstByName("Отправлена заявка");
            return relationshipMapper.toRelationshipDto(relationshipRepository.save(new Relationship(new RelationshipId(senderId, friendId),
                    null, null, relationshipStatus,  (byte) -1)));
        }
        if(Objects.equals(relationship.getRelationshipId().getFirstAcc(), senderId) && relationship.getSender() == -1
                || Objects.equals(relationship.getRelationshipId().getSecondAcc(), senderId) && relationship.getSender() == 1)
            throw new ResourceNotAllowedException(String.format("You sent a friend request to the user %d", friendId));

        relationshipStatus = relationshipStatusRepository.findFirstByName("Дружба");
        relationship.setStatus(relationshipStatus);
        relationship.setSender((byte) 0);
        return relationshipMapper.toRelationshipDto(relationshipRepository.save(relationship));
    }

    @Override
    public void deleteFriend(Long senderId, Long friendId) {
        Relationship relationship = relationshipRepository.findByFirstAccAndSecondAcc(senderId, friendId);
        boolean senderIsFirst;

        if(relationship == null){
            throw new ResourceNotFoundException(String.format("Users %d, %d have no relationship", senderId, friendId));
        }

        senderIsFirst = Objects.equals(relationship.getRelationshipId().getFirstAcc(), senderId);

        if(senderIsFirst && relationship.getSender() == 1 || !senderIsFirst && relationship.getSender() == -1)
            throw new ResourceNotAllowedException(String.format("User %d is not your friend", friendId));

        if(relationship.getSender() == 0){
            relationship.setStatus(relationshipStatusRepository.findFirstByName("Отправлена заявка"));
            if(senderIsFirst)
                relationship.setSender((byte) 1);
            else
                relationship.setSender((byte) -1);
            relationshipRepository.save(relationship);
            return;
        }

        relationshipRepository.delete(relationship);
    }

    @Override
    public RelationshipDto getRelationship(Long senderId, Long friendId) {
        Relationship relationship = relationshipRepository.findByFirstAccAndSecondAcc(senderId, friendId);
        if(relationship == null){
            throw new ResourceNotFoundException(String.format("Users %d, %d have no relationship", senderId, friendId));
        }
        return relationshipMapper.toRelationshipDto(relationship);
    }

    @Override
    public Page<AccountItemDto> getFriends(Long senderId, PageDto pageDto) {
        return relationshipRepository.getFriends(senderId, pageDto.getPageable()).map(i -> {
            Account account;
            if(i.getFirstAcc().equals(senderId))
                account = accountRepository.findById(i.getSecondAcc())
                        .orElseThrow(()->new ResourceNotFoundException("User " + i.getSecondAcc() + " not found"));
            else
                account = accountRepository.findById(i.getFirstAcc())
                        .orElseThrow(()->new ResourceNotFoundException("User " + i.getFirstAcc() + " not found"));

            return accountMapper.toAccountItemDto(account);
        });
    }
}
