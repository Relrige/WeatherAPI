package com.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WeatherFetcher weatherFetcher = new WeatherFetcher();
        List<String> cities = List.of("Chisinau", "Madrid", "Kyiv", "Amsterdam");
        List<WeatherFetcher.CityResult> results = weatherFetcher.fetchAll(cities);
        WeatherTable weatherTable = new WeatherTable(results);
        weatherTable.outputTable();
        weatherFetcher.shutdown();
    }
}
