package com.dsr.linker.service;

import com.dsr.linker.dto.*;
import com.dsr.linker.entity.Account;
import com.dsr.linker.entity.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    AccountDto saveAccount(AccountDto accountDto);
    AccountDto activateAccount(String activationCode);
    AccountDto updateAccount(AccountDto accountDto);
    AccountForUserDto getAccount(Long id);
    Page<AccountItemDto> getAccounts(AccountForSearchDto accountDto, PageDto pageDto);
    List<Role> getRolesByEmailForToken(String email);
}
