package com.example.weather.interfaces;

//import com.example.weather.application.WeatherService;
import com.example.weather.domain.CurrentWeather;
import com.example.weather.domain.DailyWeather;
import com.example.weather.service.WeatherService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DailyWeatherController {



    @GetMapping("/weathers/daily/{city}")
    public List<DailyWeather> getDailyWeather(@PathVariable("city") String city) throws IOException, ParseException {

        WeatherService weatherService = new WeatherService();

        return weatherService.getDailyWeatherData(city);
    }

}