package com.esame.weather.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esame.weather.client.CountryClient;
import com.esame.weather.client.WeatherClient;
import com.esame.weather.dto.CountryApiResponse;
import com.esame.weather.dto.CountryWeatherDTO;

import com.esame.weather.entity.CountryWeatherEntity;
import com.esame.weather.mapper.CountryMapper;

import com.esame.weather.repository.CountryWeatherRepository;

@Service
public class CountryWeatherService {

    @Autowired
    private CountryClient countryClient;

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private CountryWeatherRepository repository;

    /**
     * Metodo principale per ottenere dati paese + meteo, mappare, salvare e
     * restituire DTO.
     */
    public CountryWeatherDTO getCountryWeather(String countryName) {
        CountryApiResponse countryResponse = countryClient.fetchCountryData(countryName);

        CountryWeatherDTO dto;

        if (countryResponse.getCapitalInfo() != null
                && countryResponse.getCapitalInfo().getLatlng() != null
                && countryResponse.getCapitalInfo().getLatlng().size() == 2) {

            double lat = countryResponse.getCapitalInfo().getLatlng().get(0);
            double lon = countryResponse.getCapitalInfo().getLatlng().get(1);

            var weatherResponse = weatherClient.fetchWeatherData(lat, lon);

            // Mappo i dati country + meteo in un unico DTO
            dto = countryMapper.toCountryWeatherDto(countryName, countryResponse, weatherResponse);
        } else {
            // Mappo solo i dati country se non disponibili i dati meteo
            dto = countryMapper.toCountryWeatherDto(countryName, countryResponse, null);
        }

        dto.setRetrievedAt(java.time.LocalDateTime.now());

        CountryWeatherEntity entity = mapDtoToEntity(dto);

        Optional<CountryWeatherEntity> existing = repository.findByCountry(countryName);
        existing.ifPresent(e -> entity.setId(e.getId()));

        repository.save(entity);

        return dto;
    }

    /**
     * Mappa il DTO in Entity per la persistenza su DB.
     */
    private CountryWeatherEntity mapDtoToEntity(CountryWeatherDTO dto) {
        CountryWeatherEntity entity = new CountryWeatherEntity();

        entity.setCountry(dto.getCountry());
        entity.setCapital(dto.getCapital());
        entity.setPopulation(dto.getPopulation());
        entity.setCurrency(dto.getCurrency());
        entity.setFlagPng(dto.getFlagPng());
        entity.setVisited(dto.getVisited());
        entity.setNotes(dto.getNotes());
        entity.setRating(dto.getRating());

        // Aggiunti:
        entity.setTemperature(dto.getTemperature());
        entity.setRetrievedAt(dto.getRetrievedAt());

        return entity;
    }
}
