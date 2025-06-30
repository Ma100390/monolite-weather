package com.esame.weather.service;

import com.esame.weather.dto.CountryWeatherDTO;
import com.esame.weather.dto.CountryWeatherUpdateRequest;
import com.esame.weather.entity.CountryWeatherEntity;

public interface ICountryWeatherService {

    CountryWeatherDTO getCountryWeather(String countryName);

    CountryWeatherEntity updateCountryWeather(String country, CountryWeatherUpdateRequest updateRequest);

    Iterable<CountryWeatherEntity> getAllCountryWeather();
}
