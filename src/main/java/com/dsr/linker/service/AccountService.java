package com.dsr.linker.service;

import com.dsr.linker.dto.AccountDto;
import com.dsr.linker.dto.AuthenticationRequestDto;
import com.dsr.linker.entity.Account;
import com.dsr.linker.entity.Role;

import java.util.List;

public interface AccountService {
    AccountDto saveAccount(AccountDto accountDto);
    AccountDto activateAccount(String activationCode);
    AccountDto getAccount(String email);
    List<AccountDto> getAccounts();
    List<Role> getRolesByEmailForToken(String email);
}
