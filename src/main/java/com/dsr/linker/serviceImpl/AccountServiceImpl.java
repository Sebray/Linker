package com.dsr.linker.serviceImpl;

import com.dsr.linker.dto.*;
import com.dsr.linker.dto.account.AccountDto;
import com.dsr.linker.dto.account.AccountForSearchDto;
import com.dsr.linker.dto.account.AccountForUserDto;
import com.dsr.linker.dto.account.AccountItemDto;
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
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.*;

import static java.lang.String.format;
import static java.time.LocalDate.now;

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
    public AccountDto updateAccount(AccountDto accountDto) {
        String email = accountDto.getEmail();
        Long cityId = accountDto.getCityId();
        City city = null;
        Account account;

        Optional<Account> acc = accountRepository.findById(accountDto.getId());
        if(acc.isEmpty())
            throw new ResourceNotFoundException(String.format("User with id %d does not exist", accountDto.getId()));
        else
            account = acc.get();

        if(cityId != null)
            city = cityRepository.findById(accountDto.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("No city with id = %d", cityId)));

        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        account.setBirthday(accountDto.getBirthday());
        account.setCity(city);
        account.setDescription(accountDto.getDescription());
        account.setFirstName(accountDto.getFirstName());
        account.setLastName(accountDto.getLastName());

        accountRepository.save(account);
        return accountDto;
    }

    @Override
    public AccountForUserDto getAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user with id " + id));

        return accountMapper.toAccountForUserDto(account);
    }

    @Override
    public Page<AccountItemDto> getAccounts(AccountForSearchDto accountDto, PageDto pageDto) {

        return accountRepository.findAll((Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.notEqual(root.get("id").as(Long.class), accountDto.getId()));
            if (null != accountDto.getLastName() && !"".equals(accountDto.getLastName()))
                predicates.add(cb.equal(root.get("lastName").as(String.class), accountDto.getLastName()));

            if (null != accountDto.getFirstName() && !"".equals(accountDto.getFirstName()))
                predicates.add(cb.equal(root.get("firstName").as(String.class), accountDto.getFirstName()));

            if (accountDto.getCity() != null) {
                if (null != accountDto.getCity().getId())
                    predicates.add(cb.equal(root.get("city").<Long>get("id"), accountDto.getCity().getId()));
            }

            if (accountDto.getCity() != null) {
                if (null != accountDto.getCity().getCountryId())
                    predicates.add(cb.equal(root.get("city").get("country").<Long>get("id"), accountDto.getCity().getCountryId()));
            }

            if (accountDto.getAge() != null) {
                LocalDate endDate = now().minusYears(accountDto.getAge());
                LocalDate startDate = endDate.minusYears(1);
                predicates.add(cb.greaterThan(root.get("birthday").as(LocalDate.class), startDate));
                predicates.add(cb.lessThanOrEqualTo(root.get("birthday").as(LocalDate.class), endDate));
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        }, pageDto.getPageable()).map(accountMapper::toAccountItemDto);
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
    public Long getIdByEmail(String email) {
        return accountRepository.getIdByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));

        return JwtUserFactory.create(user);
    }
}
