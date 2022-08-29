package com.dsr.linker.controller;

import com.dsr.linker.dto.account.AccountItemDto;
import com.dsr.linker.dto.PageDto;
import com.dsr.linker.dto.RelationshipDto;
import com.dsr.linker.security.JwtUser;
import com.dsr.linker.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/friends")
@RequiredArgsConstructor
public class RelationshipController {
    private final RelationshipService relationshipService;

    @PostMapping("/{id}")
    public ResponseEntity<RelationshipDto> addFriend(@PathVariable Long id){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(relationshipService.addFriend(user.getId(), id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFriend(@PathVariable Long id){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        relationshipService.deleteFriend(user.getId(), id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelationshipDto> getRelationship(@PathVariable Long id){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(relationshipService.getRelationship(user.getId(), id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<AccountItemDto>> getFriends(PageDto pageDto){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(relationshipService.getFriends(user.getId(), pageDto),HttpStatus.OK);
    }
}
