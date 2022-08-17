package com.dsr.linker.serviceImpl;

import com.dsr.linker.dto.AccountDto;
import com.dsr.linker.entity.Account;
import com.dsr.linker.entity.AccountStatus;
import com.dsr.linker.entity.City;
import com.dsr.linker.entity.Role;
import com.dsr.linker.exception.ResourceAlreadyExistsException;
import com.dsr.linker.exception.ResourceNotFoundException;
import com.dsr.linker.mapper.AccountMapper;
import com.dsr.linker.repository.AccountRepository;
import com.dsr.linker.repository.AccountStatusRepository;
import com.dsr.linker.repository.CityRepository;
import com.dsr.linker.repository.RoleRepository;
import com.dsr.linker.security.JwtUserFactory;
import com.dsr.linker.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService{
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final CityRepository cityRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    private final MailSender mailSender;
//
//    @Override
//    public Account saveAccount(AuthenticationRequestDto account) {
//        String check = passwordEncoder.encode(account.getPassword());
//        account.setPassword(passwordEncoder.encode(account.getPassword()));
//        return new Account();
//        //return accountRepository.save(account);
//    }

    @Override
    public AccountDto saveAccount(AccountDto accountDto) {
        String email = accountDto.getEmail();
        Long cityId = accountDto.getCityId();
        String password = passwordEncoder.encode(accountDto.getPassword());
        String activationCode;
        City city = null;

        Optional<Account> account = accountRepository.findByEmail(email);

        if ((account.isPresent() && !account.get().getActivationCode().isEmpty()))
            throw new ResourceAlreadyExistsException(String.format("User with email %s already exists", email));

        if(account.isPresent() && account.get().getStatus().getName().equals("Заблокирован"))
            throw new ResourceAlreadyExistsException(String.format("User with email %s is banned", email));

        if(cityId != null)
            city = cityRepository.findById(accountDto.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("No city with id = %d", cityId)));

        Role role = roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new ResourceNotFoundException("ROLE_USER does not exist"));

        AccountStatus accountStatus = accountStatusRepository.findByName("Не активирован")
                .orElseThrow(() -> new ResourceNotFoundException("Status does not exist"));

        activationCode = UUID.randomUUID().toString();

        String message = String.format(
                "Здравствуйте, %s! \n" + ". Чтобы активировать учетную запись, пожалуйста, перейдтие по ссылке: http://localhost:8080/api/reg/activate/%s",
                accountDto.getFirstName(), activationCode);
        mailSender.send(email, "Activation code", message);

        accountRepository.save(new Account(null, email, password, role, accountDto.getBirthday(),
                city, null, accountDto.getFirstName(),
                accountDto.getLastName(), accountDto.getDescription(), activationCode, accountStatus));

        return accountDto;
    }

    @Override
    public AccountDto activateAccount(String activationCode) {
        Account account = accountRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new ResourceNotFoundException("User with this activation code does not exist"));
        AccountStatus accountStatus = accountStatusRepository.findByName("Активирован")
                .orElseThrow(() -> new ResourceNotFoundException("Status does not exist"));

        account.setStatus(accountStatus);
        account.setActivationCode(null);
        accountRepository.save(account);

        return accountMapper.toAccountDto(account);
    }

    @Override
    public AccountDto getAccount(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No user with email " + email));

        return accountMapper.toAccountDto(account);
    }

    @Override
    public List<AccountDto> getAccounts() {
        return  accountMapper.toAccountDto(accountRepository.findAll());
    }

    @Override
    public List<Role> getRolesByEmailForToken(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No user with email " + email));

        List<Role> roles = new ArrayList<>();
        roles.add(account.getRole());
        return roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));

        return JwtUserFactory.create(user);
    }
}
