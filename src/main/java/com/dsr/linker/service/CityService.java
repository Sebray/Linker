package com.dsr.linker.service;

import com.dsr.linker.dto.CityDto;

import java.util.List;

public interface CityService {
    List<CityDto> getCitiesByCountryId(Long countryId);
}
