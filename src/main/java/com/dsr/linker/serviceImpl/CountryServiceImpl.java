package com.dsr.linker.serviceImpl;

import com.dsr.linker.dto.CountryDto;
import com.dsr.linker.mapper.CountryMapper;
import com.dsr.linker.repository.CountryRepository;
import com.dsr.linker.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryDto> getCountries() {
        return countryMapper.toCountryDto(countryRepository.findAll());
    }
}
