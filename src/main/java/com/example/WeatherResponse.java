package com.example;

import com.google.gson.annotations.SerializedName;
import java.util.List;

record Hour(
        @SerializedName("wind_dir") String windDir
) {}

record Day(
        @SerializedName("mintemp_c") double minTempC,
        @SerializedName("maxtemp_c") double maxTempC,
        @SerializedName("avghumidity") int avgHumidity,
        @SerializedName("maxwind_kph") double maxWindKph
) {}

record ForecastDay(
        @SerializedName("date") String date,
        @SerializedName("day") Day day,
        @SerializedName("hour") List<Hour> hour
) {}

record Forecast(
        @SerializedName("forecastday") List<ForecastDay> forecastDayList
) {}

public record WeatherResponse(
        @SerializedName("forecast") Forecast forecast
) {}