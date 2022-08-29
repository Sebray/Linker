package com.dsr.linker.controller;

import com.dsr.linker.dto.account.AccountDto;
import com.dsr.linker.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reg")
@RequiredArgsConstructor
public class RegistrationController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto account) {
        return new ResponseEntity<>(accountService.saveAccount(account), HttpStatus.OK);
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<AccountDto> activate(@PathVariable String code) {
        return new ResponseEntity<>(accountService.activateAccount(code),HttpStatus.OK);
    }
}
