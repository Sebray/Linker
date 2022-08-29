package com.dsr.linker.mapper;

import com.dsr.linker.dto.account.AccountDto;
import com.dsr.linker.dto.account.AccountForUserDto;
import com.dsr.linker.dto.account.AccountItemDto;
import com.dsr.linker.dto.CityDto;
import com.dsr.linker.entity.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    public AccountDto toAccountDto(Account account){
        boolean existCity = account.getCity() != null;
        return new AccountDto(account.getId(), account.getEmail(), account.getPassword(),
                account.getFirstName(), account.getLastName(), account.getBirthday(),
                (existCity ? account.getCity().getId() : null),
                account.getDescription());
    }

    public AccountForUserDto toAccountForUserDto(Account account){
        boolean existCity = account.getCity() != null;
        return new AccountForUserDto(account.getId(),
                account.getFirstName(), account.getLastName(), account.getBirthday(),
                (existCity ? new CityDto(account.getCity().getId(),
                        account.getCity().getName(),
                        account.getCity().getCountry().getId())
                        : null),
                account.getDescription());
    }

    public AccountItemDto toAccountItemDto(Account account){
        boolean existCity = account.getCity() != null;
        return new AccountItemDto(account.getId(),
                account.getFirstName(), account.getLastName(), account.getBirthday(),
                (existCity ? new CityDto(account.getCity().getId(),
                        account.getCity().getName(), null) : null));
    }

    public List<AccountDto> toAccountDto(List<Account> accounts) {
        return accounts.stream().map(this::toAccountDto).collect(Collectors.toList());
    }

}
