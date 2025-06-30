package com.esame.weather.mapper;

import org.springframework.stereotype.Component;

import com.esame.weather.dto.CountryApiResponse;
import com.esame.weather.dto.CountryWeatherDTO;
import com.esame.weather.dto.OpenMeteoCurrentDTO;

@Component
public class CountryMapper {

    public CountryWeatherDTO toCountryWeatherDto(String countryName, CountryApiResponse apiResponse,
            OpenMeteoCurrentDTO weather) {
        CountryWeatherDTO dto = new CountryWeatherDTO();
        dto.setCountry(countryName);

        if (apiResponse.getCapital() != null && !apiResponse.getCapital().isEmpty()) {
            dto.setCapital(apiResponse.getCapital().get(0));
        } else {
            dto.setCapital("N/A");
        }

        dto.setPopulation(apiResponse.getPopulation());

        if (apiResponse.getCurrencies() != null && !apiResponse.getCurrencies().isEmpty()) {
            String currencyCode = apiResponse.getCurrencies().keySet().iterator().next();
            dto.setCurrency(currencyCode);
        } else {
            dto.setCurrency("N/A");
        }

        if (apiResponse.getFlags() != null) {
            String flagPng = apiResponse.getFlags().get("png");
            dto.setFlagPng(flagPng != null ? flagPng : "N/A");
        } else {
            dto.setFlagPng("N/A");
        }

        if (weather != null) {
            dto.setTemperature(weather.getTemperature_2m()); // da double a Double si fa boxing automatico
        } else {
            dto.setTemperature(null);
        }

        return dto;
    }
}
