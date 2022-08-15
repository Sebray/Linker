package com.dsr.linker.serviceImpl;

import com.dsr.linker.dto.AccountDto;
import com.dsr.linker.dto.AuthenticationRequestDto;
import com.dsr.linker.entity.Account;
import com.dsr.linker.mapper.AccountMapper;
import com.dsr.linker.repository.AccountRepository;
import com.dsr.linker.security.JwtUser;
import com.dsr.linker.security.JwtUserFactory;
import com.dsr.linker.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService{
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    @Override
    public Account saveAccount(AuthenticationRequestDto account) {
        String check = passwordEncoder.encode(account.getPassword());
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return new Account();
        //return accountRepository.save(account);
    }

    @Override
    public Account getAccount(String email) {
        return accountRepository.findByEmail(email).get();
    }

    @Override
    public List<AccountDto> getAccounts() {
        return  accountMapper.toAccountDto(accountRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByEmail(username).get();

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
