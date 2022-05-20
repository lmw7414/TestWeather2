package com.example.weather.interfaces;

import com.example.weather.domain.DailyWeather;
import com.example.weather.domain.ScoreFilter;
import com.example.weather.service.WeatherService;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ScoreController {
    @PostMapping("/weathers/score")
    public List<Float> calScore(@RequestBody ScoreFilter resource) throws IOException, ParseException {

        WeatherService weatherService = new WeatherService();
        List<Float> scoreset = new ArrayList<>();
        List<DailyWeather> dailyWeathers = weatherService.getDailyWeatherData(resource.getLocation());


        return null;
    }
}
