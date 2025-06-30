package com.esame.weather.dto;

import java.util.List;
import java.util.Map;

public class CountryApiResponse {

    private Name name;
    private List<String> capital;
    private long population;
    private Map<String, Currency> currencies;
    private Map<String, String> flags;
    private CapitalInfo capitalInfo;
    private List<String> borders;
    private String region;
    private Map<String, String> languages;

    public CountryApiResponse(Name name, List<String> capital, long population, Map<String, Currency> currencies,
            Map<String, String> flags, CapitalInfo capitalInfo, List<String> borders,
            String region, Map<String, String> languages) {
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.currencies = currencies;
        this.flags = flags;
        this.capitalInfo = capitalInfo;
        this.borders = borders;
        this.region = region;
        this.languages = languages;
    }
    // --- Getters e Setters ---

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public Map<String, Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, Currency> currencies) {
        this.currencies = currencies;
    }

    public Map<String, String> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, String> flags) {
        this.flags = flags;
    }

    public CapitalInfo getCapitalInfo() {
        return capitalInfo;
    }

    public void setCapitalInfo(CapitalInfo capitalInfo) {
        this.capitalInfo = capitalInfo;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

    // --- Metodi di comodo ---

    public String getCapitalName() {
        if (capital != null && !capital.isEmpty()) {
            return capital.get(0);
        }
        return null;
    }

    public String getFirstCurrencyCode() {
        if (currencies != null && !currencies.isEmpty()) {
            return currencies.keySet().iterator().next();
        }
        return null;
    }

    public Currency getFirstCurrency() {
        String code = getFirstCurrencyCode();
        if (code != null) {
            return currencies.get(code);
        }
        return null;
    }

    public String getFlagPng() {
        if (flags != null) {
            return flags.get("png");
        }
        return null;
    }

    public Double getCapitalLat() {
        if (capitalInfo != null && capitalInfo.getLatlng() != null && !capitalInfo.getLatlng().isEmpty()) {
            return capitalInfo.getLatlng().get(0);
        }
        return null;
    }

    public Double getCapitalLon() {
        if (capitalInfo != null && capitalInfo.getLatlng() != null && capitalInfo.getLatlng().size() > 1) {
            return capitalInfo.getLatlng().get(1);
        }
        return null;
    }

    // --- Classi annidate ---

    public static class Name {
        private String common;
        private String official;
        private Map<String, NativeName> nativeName;

        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }

        public String getOfficial() {
            return official;
        }

        public void setOfficial(String official) {
            this.official = official;
        }

        public Map<String, NativeName> getNativeName() {
            return nativeName;
        }

        public void setNativeName(Map<String, NativeName> nativeName) {
            this.nativeName = nativeName;
        }
    }

    public static class NativeName {
        private String official;
        private String common;

        public String getOfficial() {
            return official;
        }

        public void setOfficial(String official) {
            this.official = official;
        }

        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }
    }

    public static class Currency {
        private String name;
        private String symbol;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }

    public static class CapitalInfo {
        private List<Double> latlng;

        public List<Double> getLatlng() {
            return latlng;
        }

        public void setLatlng(List<Double> latlng) {
            this.latlng = latlng;
        }
    }
}
