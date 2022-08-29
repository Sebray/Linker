package com.dsr.linker.controller;

import com.dsr.linker.dto.CityDto;
import com.dsr.linker.dto.CountryDto;
import com.dsr.linker.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getCountries(){
        return new ResponseEntity<>(countryService.getCountries(), HttpStatus.OK);
    }
}
