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

/*
location: string; //위치
  weather_main: string; // 날씨
  dt: string; // 날짜
  temp_max: number; // 최고온도
  temp_min: number; // 최저온도
  uvi: number; // 자외선
  humidity: number; // 습도
  rain: number; // 강수확률
  wind_speed: number; // 풍속
  score: number; // 점수


location: string; // 현재 위치
        current_temp: number; // 현재 온도
        weather_main: string; // 날씨
        todayScore: number; // 현재 점수
        current_dt: string; // 현재 날짜


    export type HourlyWeatherType = {
        weather: string; // 날씨
        temp: number; // 온도
        weather_description: string; // 날씨 상세정보
        dt: string; // 시간
        */
