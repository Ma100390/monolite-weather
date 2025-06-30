package com.esame.weather.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.esame.weather.dto.CountryApiResponse;

@Component
public class CountryClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public CountryApiResponse fetchCountryData(String countryName) {
        String url = "https://restcountries.com/v3.1/name/" + countryName;
        CountryApiResponse[] response = restTemplate.getForObject(url, CountryApiResponse[].class);

        if (response == null || response.length == 0) {
            throw new RuntimeException("Paese non trovato: " + countryName);
        }

        return response[0];
    }
}