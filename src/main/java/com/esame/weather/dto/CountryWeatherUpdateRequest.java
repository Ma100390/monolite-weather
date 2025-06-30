package com.esame.weather.dto;

public class CountryWeatherUpdateRequest {

    private Boolean visited;
    private String notes;
    private Integer rating;

    // Costruttore vuoto (necessario per deserializzazione JSON)
    public CountryWeatherUpdateRequest() {
    }

    // Getters e Setters
    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    // Optional: toString() utile per debug/logging
    @Override
    public String toString() {
        return "CountryWeatherUpdateRequest{" +
                "visited=" + visited +
                ", notes='" + notes + '\'' +
                ", rating=" + rating +
                '}';
    }
}