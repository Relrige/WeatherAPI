package com.example;

import com.example.retrofit.WeatherService;
import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    private final static Dotenv dotenv = Dotenv.load();
    private static WeatherService service;

    public static String getEnv(String variable) {
        return dotenv.get(variable);
    }

    public static WeatherService getWeatherService() {
        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.weatherapi.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(WeatherService.class);
        }
        return service;
    }
}
