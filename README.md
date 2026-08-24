# Weather Forecast Table

Fetches tomorrow's forecast for a list of cities from [WeatherAPI](https://www.weatherapi.com/) in parallel and prints a formatted table with min/max temperature, humidity, and average wind speed/direction.

## Requirements

- Java 17+
- Gradle (or use the included wrapper, ./gradlew)
- A free API key from [weatherapi.com](https://www.weatherapi.com/)

## Setup

1. Clone the repo.
2. Create a `.env` file in the project root:

   ```
   WEATHER_API_KEY=your_api_key_here
   ```

3. Build and run:

   ```bash
   ./gradlew run
   ```

## How it works

- `Main` kicks things off with a hardcoded list of cities (edit `Main.java` to change them).
- `WeatherFetcher` calls the WeatherAPI forecast endpoint concurrently for each city via a fixed thread pool and `CompletableFuture`.
- `WindCalculator` computes average wind speed and a circular mean for wind direction (a plain arithmetic mean breaks near the 0°/360° boundary), then converts the result to a 16-point compass label.
- `WeatherTable` formats the results into a console table.

## Project structure

```
com.example
├── Main.java              # entry point
├── Config.java            # loads .env, builds the shared Retrofit/WeatherService instance
├── WeatherFetcher.java    # fetches forecasts in parallel
├── WeatherTable.java      # formats results as a table
├── retrofit/
│   ├── WeatherResponse.java   # API response models
│   └── WeatherService.java    # Retrofit interface
└── utils/
    ├── WindCalculator.java    # avg speed + circular mean wind direction
    └── WindSummary.java       # information about wind record
```
