package com.example.utils;

import com.example.retrofit.WeatherResponse.*;

import java.util.List;

public class WindCalculator {

    public static WindSummary calculateAvgWind(List<Hour> hours) {
        if (hours == null || hours.isEmpty()) {
            return new WindSummary(0.0, "N/A");
        }
        double avgSpeed = hours.stream()
                .mapToDouble(Hour::windKph)
                .average().orElse(0.0);

        // Circular mean: convert each direction to a unit vector,
        // average the vectors, then convert back to an angle.
        double sinSum = 0.0;
        double cosSum = 0.0;
        for (Hour h : hours) {
            double rad = Math.toRadians(h.windDegree());
            sinSum += Math.sin(rad);
            cosSum += Math.cos(rad);
        }

        double avgDirection = Math.toDegrees(Math.atan2(sinSum / hours.size(), cosSum / hours.size()));
        if (avgDirection < 0) {
            avgDirection += 360;
        }

        return new WindSummary(avgSpeed, toCompass(avgDirection));
    }
    private static final String[] COMPASS = {
            "N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
            "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"
    };

    public static String toCompass(double degrees) {
        int index = (int) Math.round(degrees / 22.5) % 16;
        return COMPASS[index];
    }
}
