package com.example;

import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class WeatherFetcher {
    private final WeatherService service;
    private final String apiKey;

    public record CityResult(String city, ForecastDay forecast) {}

    public WeatherFetcher() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("WeatherApi");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(WeatherService.class);
    }

    public void fetch() {
        List<String> cities = List.of("Chisinau", "Madrid", "Kyiv", "Amsterdam");

        List<CompletableFuture<CityResult>> futures = cities.stream()
                .map(city -> CompletableFuture.supplyAsync(() -> fetchNextDayForecast(city)))
                .toList();

        List<CityResult> results = futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList();

        if (results.isEmpty()) {
            System.out.println("No data retrieved.");
            return;
        }

        String targetDate = results.get(0).forecast().date();

        System.out.println("-".repeat(85));
        System.out.printf("%-12s | %-70s%n", "City", "Date: " + targetDate);
        System.out.printf("%-12s | %-12s | %-12s | %-12s | %-12s | %-10s%n",
                "", "Min Temp(°C)", "Max Temp(°C)", "Humidity(%)", "Wind(kph)", "Wind Dir");
        System.out.println("-".repeat(85));

        for (CityResult res : results) {
            Day day = res.forecast().day();
            String noonWindDir = res.forecast().hour().get(12).windDir();

            System.out.printf("%-12s | %-12.1f | %-12.1f | %-12d | %-12.1f | %-10s%n",
                    res.city(),
                    day.minTempC(),
                    day.maxTempC(),
                    day.avgHumidity(),
                    day.maxWindKph(),
                    noonWindDir);
        }
        System.out.println("-".repeat(85));
    }

    public CityResult fetchNextDayForecast(String city) {
        try {
            Response<WeatherResponse> response = service.getForecast(apiKey, city, 2).execute();

            if (response.isSuccessful() && response.body() != null) {
                ForecastDay tomorrow = response.body().forecast().forecastDayList().get(1);
                return new CityResult(city, tomorrow);
            } else {
                System.err.println("Failed to fetch data for " + city + ". Code: " + response.code());
            }
        } catch (Exception e) {
            System.err.println("Error fetching data for " + city + ": " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        WeatherFetcher weatherFetcher = new WeatherFetcher();
        weatherFetcher.fetch();
    }
}