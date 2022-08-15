package com.dsr.linker.mapper;

import com.dsr.linker.dto.AccountDto;
import com.dsr.linker.entity.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    public AccountDto toAccountDto(Account account){
        return new AccountDto(account.getId(), account.getEmail(), account.getPassword(),
                account.getFirstName(), account.getLastName(), account.getBirthday(), account.getCity().getId(), account.getDescription());
    }

    public List<AccountDto> toAccountDto(List<Account> accounts) {
        return accounts.stream().map(this::toAccountDto).collect(Collectors.toList());
    }

}
