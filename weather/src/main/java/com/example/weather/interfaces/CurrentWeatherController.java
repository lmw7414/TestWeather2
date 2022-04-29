package com.example.weather.interfaces;

//import com.example.weather.application.WeatherService;

import com.example.weather.domain.CurrentWeather;
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

@RestController
public class CurrentWeatherController {


    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/onecall";
    private final String apiKey = "7e794e5e8d90a420c85cddb7aeb9358e"; // 발급받은 API key

    @GetMapping("/weathers/current/{city}")
    public CurrentWeather getCurrentWeather(@PathVariable("city") String city)
            throws IOException, ParseException {


        float arr[] = getGeoDataByAddress(city);

        float lat = (float)arr[0];
        float lon = (float)arr[1];

        String result = "";
        CurrentWeather currentWeather = new CurrentWeather();


        URL url = new URL(BASE_URL + "?lat=" + lat + "&lon=" + lon + "&exclude=hourly,minutely" + "&appid=" + apiKey);

        BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = bf.readLine();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
        JSONObject currentObject = (JSONObject) jsonObject.get("current");
        JSONArray currentWeatherObject = (JSONArray) currentObject.get("weather");
        JSONObject currentWeatherData = (JSONObject) currentWeatherObject.get(0);

        currentWeather.setLat(lat);
        currentWeather.setLon(lon);
        currentWeather.setTimezone((String) jsonObject.get("timezone"));
        currentWeather.setTimezone_offset((long) jsonObject.get("timezone_offset"));
        currentWeather.setCurrent_dt(currentWeather.changeUnixTime(currentObject.get("dt").toString()));
        currentWeather.setCurrent_sunrise(currentWeather.changeUnixTime(currentObject.get("sunrise").toString()));
        currentWeather.setCurrent_sunset(currentWeather.changeUnixTime(currentObject.get("sunset").toString()));
        currentWeather.setCurrent_temp(Float.parseFloat(currentObject.get("temp").toString())-273.15f);
        currentWeather.setCurrent_feels_like(Float.parseFloat(currentObject.get("feels_like").toString())-273.15f);
        currentWeather.setCurrent_pressure((long) currentObject.get("pressure"));
        currentWeather.setCurrent_humidity(Float.parseFloat(currentObject.get("humidity").toString()));
        currentWeather.setCurrent_dew_point(Float.parseFloat(currentObject.get("dew_point").toString())-273.15f);
        currentWeather.setCurrent_uvi(Float.parseFloat(currentObject.get("uvi").toString()));
        currentWeather.setCurrent_clouds(Integer.parseInt(currentObject.get("clouds").toString()));
        currentWeather.setCurrent_visibility(Integer.parseInt(currentObject.get("visibility").toString()));
        currentWeather.setCurrent_wind_speed(Float.parseFloat(currentObject.get("wind_speed").toString()));
        currentWeather.setCurrent_wind_deg(Integer.parseInt(currentObject.get("wind_deg").toString()));
        currentWeather.setCurrent_wind_gust(Float.parseFloat(currentObject.get("wind_gust").toString()));
        currentWeather.setWeather_id(Integer.parseInt(currentWeatherData.get("id").toString()));
        currentWeather.setWeather_main((String) currentWeatherData.get(("main")));
        currentWeather.setWeather_description((String) currentWeatherData.get(("description")));
        currentWeather.setWeather_icon((String) currentWeatherData.get(("icon")));


        return currentWeather;
    }


    public static float[] getGeoDataByAddress(String completeAddress) {

        float[] arr = new float[2];
        String result = "";
        try {
            String API_KEY = "AIzaSyDmc0I-f4BJedfRyA6jJuSBX8JuVRpPT1g";
            String surl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(completeAddress, "UTF-8") + "&key=" + API_KEY;

            StringBuilder responseStrBuilder = new StringBuilder();


            URL url = new URL(surl);

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            while ((result = bf.readLine()) != null) {
                responseStrBuilder.append(result);
            }


            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseStrBuilder.toString());

            JSONArray results = (JSONArray) jsonObject.get("results");
            JSONObject jsonObject1 = (JSONObject) results.get(0);
            JSONObject jsonObject2 = (JSONObject) jsonObject1.get("geometry");
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("location");
            float lat = Float.parseFloat(jsonObject3.get("lat").toString());
            float lon = Float.parseFloat(jsonObject3.get("lng").toString());


            arr[0] = lat;
            arr[1] = lon;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

}