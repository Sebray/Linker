package com.dsr.linker.repository;

import com.dsr.linker.entity.City;
import com.dsr.linker.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> getFirstById(Long id);
}
