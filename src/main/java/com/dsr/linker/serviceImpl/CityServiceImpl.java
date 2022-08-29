package com.dsr.linker.serviceImpl;

import com.dsr.linker.dto.CityDto;
import com.dsr.linker.exception.ResourceNotFoundException;
import com.dsr.linker.mapper.CityMapper;
import com.dsr.linker.repository.CityRepository;
import com.dsr.linker.repository.CountryRepository;
import com.dsr.linker.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public List<CityDto> getCitiesByCountryId(Long countryId) {
        countryRepository.getFirstById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Country with id %d not found", countryId)));
        return cityMapper.toCityDto(cityRepository.findAllByCountryId(countryId));
    }
}
