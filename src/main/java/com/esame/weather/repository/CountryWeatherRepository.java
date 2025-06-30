package com.esame.weather.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.esame.weather.entity.CountryWeatherEntity;

@Repository
public interface CountryWeatherRepository extends CrudRepository<CountryWeatherEntity, Long> {
       Optional<CountryWeatherEntity> findByCountry(String country);
}