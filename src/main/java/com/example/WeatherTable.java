package com.example;

import com.example.retrofit.WeatherResponse;
import com.example.utils.WindSummary;
import com.example.WeatherFetcher.CityResult;
import java.util.List;

import static com.example.utils.WindCalculator.calculateAvgWind;

public class WeatherTable {
    private static final String ROW_FORMAT = "%-12s | %-12s | %-12s | %-12s | %-12s | %-10s%n";
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
        sb.append(String.format(ROW_FORMAT,
                "", "Min Temp(°C)", "Max Temp(°C)", "Humidity(%)", "Wind(kph)", "Wind Dir"));
        sb.append(separator).append(System.lineSeparator());

        for (CityResult res : results) {
            WeatherResponse.Day day = res.forecast().day();
            WindSummary windSummary = calculateAvgWind(res.forecast().hour());

            sb.append(String.format(ROW_FORMAT,
                    res.city(),
                    day.minTempC(),
                    day.maxTempC(),
                    day.avgHumidity(),
                    windSummary.avgSpeedKph(),
                    windSummary.avgDirection()));
        }
        sb.append(separator);

        return sb.toString();
    }
}