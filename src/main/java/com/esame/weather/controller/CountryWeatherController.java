package com.esame.weather.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esame.weather.dto.CountryWeatherDTO;
import com.esame.weather.dto.CountryWeatherUpdateRequest;
import com.esame.weather.entity.CountryWeatherEntity;
import com.esame.weather.repository.CountryWeatherRepository;
import com.esame.weather.service.CountryWeatherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/country-weather")
public class CountryWeatherController {
    @Autowired
    private CountryWeatherRepository repository;

    @Autowired
    private CountryWeatherService countryWeatherService;

    @Operation(summary = "Ottieni meteo di un paese per nome", description = "Restituisce le informazioni meteo attuali di un paese specificato dal nome.", responses = {
            @ApiResponse(responseCode = "200", description = "Meteo recuperato con successo", content = @Content(schema = @Schema(implementation = CountryWeatherDTO.class))),
            @ApiResponse(responseCode = "404", description = "Paese/Meteo non trovato", content = @Content)
    })
    @GetMapping("/{countryName}")
    public ResponseEntity<?> getCountryWeather(
            @Parameter(description = "Nome del paese", example = "Italy", required = true) @PathVariable String countryName) {
        try {
            CountryWeatherDTO result = countryWeatherService.getCountryWeather(countryName);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(404)
                    .body("Errore: " + e.getMessage());
        }
    }

    @Operation(summary = "Aggiorna dati meteo di un paese", description = "Aggiorna i dati memorizzati relativi al meteo di un paese specificato.", responses = {
            @ApiResponse(responseCode = "200", description = "Aggiornamento effettuato", content = @Content(schema = @Schema(implementation = CountryWeatherEntity.class))),
            @ApiResponse(responseCode = "404", description = "Paese non trovato", content = @Content)
    })
    @PutMapping("/{country}")
    public ResponseEntity<CountryWeatherEntity> updateCountryWeather(
            @Parameter(description = "Nome del paese", example = "Italy", required = true) @PathVariable String country,
            @RequestBody CountryWeatherUpdateRequest updateRequest) {

        Optional<CountryWeatherEntity> optionalEntity = repository.findByCountry(country);

        if (optionalEntity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        CountryWeatherEntity entity = optionalEntity.get();

        entity.setRetrievedAt(LocalDateTime.now());

        // Campi opzionali
        if (updateRequest.getVisited() != null)
            entity.setVisited(updateRequest.getVisited());

        if (updateRequest.getNotes() != null)
            entity.setNotes(updateRequest.getNotes());

        if (updateRequest.getRating() != null)
            entity.setRating(updateRequest.getRating());

        repository.save(entity);

        return ResponseEntity.ok(entity);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCountryWeather() {
        try {
            Iterable<CountryWeatherEntity> allEntries = repository.findAll();

            if (!allEntries.iterator().hasNext()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(allEntries);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il recupero della lista: " + e.getMessage());
        }
    }
}