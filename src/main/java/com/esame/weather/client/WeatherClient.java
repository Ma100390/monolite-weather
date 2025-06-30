package com.esame.weather.client;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.esame.weather.dto.OpenMeteoCurrentDTO;

@Component
public class WeatherClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public OpenMeteoCurrentDTO fetchWeatherData(double lat, double lon) {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat +
                "&longitude=" + lon +
                "&current=temperature_2m,weather_code";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Object> currentMap = (Map<String, Object>) response.get("current");

        return new OpenMeteoCurrentDTO(
                (double) currentMap.get("temperature_2m"),
                (int) currentMap.get("weather_code"),
                (String) currentMap.get("time")
        );
    }
}