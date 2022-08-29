package com.dsr.linker.repository;

import com.dsr.linker.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByActivationCode(String activationCode);
    Page<Account> findAll(Specification<Account> specification, Pageable pageable);

    @Query("select a.id " +
            "from Account a " +
            "where a.email like :email")
    Long getIdByEmail(String email);

}
