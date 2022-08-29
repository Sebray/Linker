package com.dsr.linker.controller;

import com.dsr.linker.dto.*;
import com.dsr.linker.dto.account.AccountDto;
import com.dsr.linker.dto.account.AccountForSearchDto;
import com.dsr.linker.dto.account.AccountForUserDto;
import com.dsr.linker.dto.account.AccountItemDto;
import com.dsr.linker.exception.ResourceNotAllowedException;
import com.dsr.linker.security.JwtUser;
import com.dsr.linker.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<Page<AccountItemDto>> getAccounts(AccountForSearchDto accountDto, PageDto pageDto){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accountDto.setId(user.getId());
        return new ResponseEntity<>(accountService.getAccounts(accountDto, pageDto), HttpStatus.OK);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountForUserDto> getAccount(@PathVariable Long id){
        return new ResponseEntity<>(accountService.getAccount(id), HttpStatus.OK);
    }

    @GetMapping("/account")
    public ResponseEntity<AccountForUserDto> getAccount(){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(accountService.getAccount(user.getId()), HttpStatus.OK);
    }

    @PutMapping("/account")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto account) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        account.setId(user.getId());

        return new ResponseEntity<>(accountService.updateAccount(account), HttpStatus.OK);
    }
}
