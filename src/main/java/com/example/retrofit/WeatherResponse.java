package com.example.retrofit;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record WeatherResponse(
        @SerializedName("forecast") Forecast forecast
) {
    public record Hour(
            @SerializedName("wind_dir") String windDir
    ) {}

    public record Day(
            @SerializedName("mintemp_c") double minTempC,
            @SerializedName("maxtemp_c") double maxTempC,
            @SerializedName("avghumidity") int avgHumidity,
            @SerializedName("maxwind_kph") double maxWindKph
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