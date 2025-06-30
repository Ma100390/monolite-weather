package com.esame.weather.dto;

public class OpenMeteoCurrentDTO {
    private Double temperature_2m;
    private int weather_code;
    private String time;

    public OpenMeteoCurrentDTO(Double temperature_2m, int weather_code, String time) {
        this.temperature_2m = temperature_2m;
        this.weather_code = weather_code;
        this.time = time;
    }

    public OpenMeteoCurrentDTO() {
    }

    public double getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(Double temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public void setWeather_code(int weather_code) {
        this.weather_code = weather_code;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public String getTime() {
        return time;
    }
}