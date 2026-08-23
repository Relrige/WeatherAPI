package com.example;

import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherFetcher {
    private final WeatherService service;
    private final String apiKey;

    public WeatherFetcher() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("WeatherApi");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(WeatherService.class);
    }

    public static void main(String[] args) {
        WeatherFetcher weatherFetcher = new WeatherFetcher();
    }
}