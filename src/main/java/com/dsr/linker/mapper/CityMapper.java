package com.dsr.linker.mapper;

import com.dsr.linker.dto.CityDto;
import com.dsr.linker.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityMapper {
    public CityDto toCityDto (City city){
        return new CityDto(city.getId(), city.getName(), city.getId());
    }

    public List<CityDto> toCityDto (List<City> cities){
        return cities.stream().map(this::toCityDto).collect(Collectors.toList());
    }
}
