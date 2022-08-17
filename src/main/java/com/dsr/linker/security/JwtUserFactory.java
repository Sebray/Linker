package com.dsr.linker.security;

import com.dsr.linker.entity.Account;
import com.dsr.linker.entity.Role;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {


    public static JwtUser create(Account account) {
        List<Role> roles = new ArrayList<>();
        roles.add(account.getRole());
        return new JwtUser(
                account.getId(),
                account.getPassword(),
                account.getEmail(),
                account.getStatus().getName().equals("Активирован"),
                mapToGrantedAuthorities(roles)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}

