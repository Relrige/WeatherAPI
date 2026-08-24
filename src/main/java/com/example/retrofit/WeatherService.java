package com.example.retrofit;

import retrofit2.Call;
import retrofit2.http.*;

public interface WeatherService {
    @GET("forecast.json")
    Call<WeatherResponse> getForecast(
            @Query("key") String apiKey,
            @Query("q") String city,
            @Query("days") int days
    );
}
