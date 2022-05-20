package com.example.weather.interfaces;

import com.example.weather.domain.DailyDust;
import com.example.weather.service.WeatherService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
public class DustController {

    @GetMapping("/weathers/dust/{city}/{start}/{end}")
    public List<DailyDust> getDust(@PathVariable("city") String city,
                                    @PathVariable("start") int start,
                                   @PathVariable("end") int end)
            throws IOException, ParseException {
        WeatherService weatherService = new WeatherService();

        return weatherService.getDust(city, start, end);
    }

}



// http://api.openweathermap.org/data/2.5/air_pollution/history?lat=35&lon=128&start=1653030556&end=1653862156&appid=7e794e5e8d90a420c85cddb7aeb9358e
