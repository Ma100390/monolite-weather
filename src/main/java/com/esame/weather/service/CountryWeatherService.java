package com.esame.weather.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esame.weather.client.CountryClient;
import com.esame.weather.client.WeatherClient;
import com.esame.weather.dto.CountryWeatherDTO;
import com.esame.weather.dto.CountryWeatherUpdateRequest;
import com.esame.weather.entity.CountryWeatherEntity;
import com.esame.weather.mapper.CountryMapper;
import com.esame.weather.repository.CountryWeatherRepository;

@Service
public class CountryWeatherService implements ICountryWeatherService {

    @Autowired
    private CountryWeatherRepository repository;

    @Autowired
    private CountryClient countryClient; // Client per ottenere i dati del paese

    @Autowired
    private WeatherClient weatherClient; // Client per ottenere i dati meteo

    @Autowired
    private CountryMapper countryMapper; // Mapper per mappare i dati in DTO

    @Override
    public CountryWeatherDTO getCountryWeather(String countryName) {
        // Recupera i dati del paese tramite il CountryClient
        var countryResponse = countryClient.fetchCountryData(countryName);

        // Dto che verrà restituito
        CountryWeatherDTO dto;

        // Se i dati relativi alla latitudine e longitudine sono disponibili
        if (countryResponse.getCapitalInfo() != null
                && countryResponse.getCapitalInfo().getLatlng() != null
                && countryResponse.getCapitalInfo().getLatlng().size() == 2) {

            double lat = countryResponse.getCapitalInfo().getLatlng().get(0);
            double lon = countryResponse.getCapitalInfo().getLatlng().get(1);

            // Recupera i dati meteo tramite il WeatherClient
            var weatherResponse = weatherClient.fetchWeatherData(lat, lon);

            // Mappa i dati paese + meteo in un unico DTO
            dto = countryMapper.toCountryWeatherDto(countryName, countryResponse, weatherResponse);
        } else {
            // Mappa solo i dati del paese se non sono disponibili i dati meteo
            dto = countryMapper.toCountryWeatherDto(countryName, countryResponse, null);
        }

        dto.setRetrievedAt(java.time.LocalDateTime.now());

        // Converte il DTO in entità per la persistenza su DB
        CountryWeatherEntity entity = mapDtoToEntity(dto);

        // Verifica se il paese esiste già nel DB
        Optional<CountryWeatherEntity> existing = repository.findByCountry(countryName);
        existing.ifPresent(e -> entity.setId(e.getId()));

        // Salva o aggiorna l'entità nel DB
        repository.save(entity);

        return dto;
    }

    @Override
    public CountryWeatherEntity updateCountryWeather(String country, CountryWeatherUpdateRequest updateRequest) {
        // Recupera l'entità dal DB
        Optional<CountryWeatherEntity> entityOpt = repository.findByCountry(country);
        
        if (entityOpt.isEmpty()) {
            throw new RuntimeException("Paese non trovato");
        }
        
        CountryWeatherEntity entity = entityOpt.get();

        // Aggiorna i campi opzionali
        if (updateRequest.getVisited() != null) entity.setVisited(updateRequest.getVisited());
        if (updateRequest.getNotes() != null) entity.setNotes(updateRequest.getNotes());
        if (updateRequest.getRating() != null) entity.setRating(updateRequest.getRating());

        // Imposta la data di recupero dei dati
        entity.setRetrievedAt(java.time.LocalDateTime.now());

        // Salva l'entità aggiornata
        return repository.save(entity);
    }

    @Override
    public Iterable<CountryWeatherEntity> getAllCountryWeather() {
        // Restituisce tutte le entità di meteo salvate nel DB
        return repository.findAll();
    }

    /**
     * Mappa il DTO in un'entità per la persistenza su DB.
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

        // Aggiungi i dati meteo
        entity.setTemperature(dto.getTemperature());
        entity.setRetrievedAt(dto.getRetrievedAt());

        return entity;
    }
}
