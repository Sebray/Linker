package com.dsr.linker.service;

import com.dsr.linker.dto.AuthenticationRequestDto;
import com.dsr.linker.entity.Account;

import java.util.List;

public interface AccountService {
    Account saveAccount(AuthenticationRequestDto account);
    Account getAccount(String email);
    List<Account> getAccounts();

}
