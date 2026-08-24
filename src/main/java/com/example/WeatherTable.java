package com.example;

import java.util.List;
import com.example.WeatherFetcher.CityResult;
import com.example.retrofit.WeatherResponse;

public class WeatherTable {
    private final List<CityResult> results;

    public WeatherTable(List<CityResult> results) {
        this.results = results;
    }

    public void outputTable() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        if (results.isEmpty()) {
            return "No data retrieved.";
        }

        StringBuilder sb = new StringBuilder();
        String separator = "-".repeat(85);
        String targetDate = results.get(0).forecast().date();

        sb.append(separator).append(System.lineSeparator());
        sb.append(String.format("%-12s | %-70s%n", "City", "Date: " + targetDate));
        sb.append(String.format("%-12s | %-12s | %-12s | %-12s | %-12s | %-10s%n",
                "", "Min Temp(°C)", "Max Temp(°C)", "Humidity(%)", "Wind(kph)", "Wind Dir"));
        sb.append(separator).append(System.lineSeparator());

        for (CityResult res : results) {
            WeatherResponse.Day day = res.forecast().day();
            String noonWindDir = res.forecast().hour().get(12).windDir();

            sb.append(String.format("%-12s | %-12.1f | %-12.1f | %-12d | %-12.1f | %-10s%n",
                    res.city(),
                    day.minTempC(),
                    day.maxTempC(),
                    day.avgHumidity(),
                    day.maxWindKph(),
                    noonWindDir));
        }
        sb.append(separator);

        return sb.toString();
    }
}