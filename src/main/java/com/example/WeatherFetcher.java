package com.example;

import com.example.retrofit.WeatherResponse;
import retrofit2.Response;
import com.example.retrofit.WeatherResponse.ForecastDay;
import com.example.retrofit.WeatherService;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherFetcher {
    private final WeatherService service;
    private final String apiKey;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private static final int FORECAST_DAYS = 2;
    private static final int TOMORROW_INDEX = 1;

    public record CityResult(String city, ForecastDay forecast) {}

    public WeatherFetcher() {
        this.apiKey = Config.getEnv("WEATHER_API_KEY");
        this.service = Config.getWeatherService();
    }

    public List<CityResult> fetchAll(List<String> cities) {
        List<CompletableFuture<CityResult>> futures = cities.stream()
                .map(city -> CompletableFuture.supplyAsync(() -> fetchNextDayForecast(city), executor))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList();
    }

    public CityResult fetchNextDayForecast(String city) {
        try {
            Response<WeatherResponse> response = service.getForecast(apiKey, city, FORECAST_DAYS).execute();

            if (response.isSuccessful() && response.body() != null) {
                ForecastDay tomorrow = response.body().forecast().forecastDayList().get(TOMORROW_INDEX);
                return new CityResult(city, tomorrow);
            } else {
                System.err.println("Failed to fetch data for " + city + ". Code: " + response.code());
            }
        } catch (Exception e) {
            System.err.println("Error fetching data for " + city + ": " + e.getMessage());
        }
        return null;
    }

    public void shutdown() {
        executor.shutdown();
    }
}