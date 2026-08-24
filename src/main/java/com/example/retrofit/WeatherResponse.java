package com.example.retrofit;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record WeatherResponse(
        @SerializedName("forecast") Forecast forecast
) {
    public record Hour(
            @SerializedName("wind_kph") double windKph,
            @SerializedName("wind_degree") int windDegree
    ) {}

    public record Day(
            @SerializedName("mintemp_c") double minTempC,
            @SerializedName("maxtemp_c") double maxTempC,
            @SerializedName("avghumidity") int avgHumidity
    ) {}

    public record ForecastDay(
            @SerializedName("date") String date,
            @SerializedName("day") Day day,
            @SerializedName("hour") List<Hour> hour
    ) {}

    public record Forecast(
            @SerializedName("forecastday") List<ForecastDay> forecastDayList
    ) {}
}