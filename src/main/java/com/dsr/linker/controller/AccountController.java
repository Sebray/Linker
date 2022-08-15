package com.dsr.linker.controller;

import com.dsr.linker.dto.AccountDto;
import com.dsr.linker.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAccounts(){
        return ResponseEntity.ok().body(accountService.getAccounts());
    }
}
