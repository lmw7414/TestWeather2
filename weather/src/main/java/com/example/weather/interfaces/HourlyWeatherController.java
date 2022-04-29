package com.example.weather.interfaces;


import com.example.weather.domain.DailyWeather;
import com.example.weather.domain.HourlyWeather;
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
import java.util.ArrayList;
import java.util.List;

@RestController
public class HourlyWeatherController {
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/onecall";
    private final String apiKey = "7e794e5e8d90a420c85cddb7aeb9358e"; // 발급받은 API key

    @GetMapping("/weathers/hourly/{lat}/{lon}")
    public List<HourlyWeather> getHourlyWeather(@PathVariable("lat") float lat,
                                                @PathVariable("lon") float lon) throws IOException, ParseException {
        String result = "";
        List<HourlyWeather> hourlyWeathers = new ArrayList<>();
        HourlyWeather hourlyWeather;


        URL url = new URL(BASE_URL + "?lat=" + lat + "&lon=" + lon + "&exclude=daily,minutely" +"&appid=" + apiKey);

        BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = bf.readLine();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONArray jsonArr = (JSONArray) jsonObject.get("hourly");



        for(int i=0; i<jsonArr.size(); i++) {
            hourlyWeather = new HourlyWeather();
            hourlyWeather.setTimezone((String) jsonObject.get("timezone"));

            JSONObject hourly = (JSONObject)jsonArr.get(i);
            JSONArray hourlyWeatherArray = (JSONArray) hourly.get("weather");
            JSONObject hourlyWeatherData = (JSONObject)hourlyWeatherArray.get(0);
            hourlyWeather.setWeather((String) hourlyWeatherData.get("main"));
//            JSONObject dailyWeatherData = (JSONObject)dailyWeatherArray.get(0);

            hourlyWeather.setDt(hourlyWeather.changeUnixTime(hourly.get("dt").toString()));

            hourlyWeathers.add(hourlyWeather);

        }

        return hourlyWeathers;
    }

}
