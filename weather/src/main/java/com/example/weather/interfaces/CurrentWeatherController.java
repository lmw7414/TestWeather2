package com.example.weather.interfaces;

//import com.example.weather.application.WeatherService;

import com.example.weather.domain.CurrentWeather;
import com.example.weather.service.WeatherService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class CurrentWeatherController {

    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/onecall";
    private final String apiKey = "7e794e5e8d90a420c85cddb7aeb9358e"; // 발급받은 API key

    @GetMapping("/weathers/current/{city}")
    public CurrentWeather getCurrentWeather(@PathVariable("city") String city) throws IOException, ParseException {

        WeatherService weatherService = new WeatherService();

        return weatherService.getCurrentWeatherData(city);
    }
}