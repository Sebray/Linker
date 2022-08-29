package com.dsr.linker.mapper;

import com.dsr.linker.dto.CountryDto;
import com.dsr.linker.entity.Country;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryMapper {
    public CountryDto toCountryDto (Country country){
        return new CountryDto(country.getId(), country.getName());
    }

    public List<CountryDto> toCountryDto (List<Country> countries){
        return countries.stream().map(this::toCountryDto).collect(Collectors.toList());
    }
}
