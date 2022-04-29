package com.example.weather.interfaces;

//import com.example.weather.application.WeatherService;
import com.example.weather.domain.CurrentWeather;
import com.example.weather.domain.DailyWeather;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
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

    //@Autowired
    //private WeatherService weatherService;

    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/onecall";
    private final String apiKey = "7e794e5e8d90a420c85cddb7aeb9358e"; // 발급받은 API key

    @GetMapping("/weathers/daily/{city}")
    public List<DailyWeather> getDailyWeather(@PathVariable("city") String city)
            throws IOException, ParseException {

        float arr[] = getGeoDataByAddress(city);

        float lat = (float)arr[0];
        float lon = (float)arr[1];

        String result = "";
        List<DailyWeather> dailyWeathers = new ArrayList<>();
        DailyWeather dailyWeather;


            URL url = new URL(BASE_URL + "?lat=" + lat + "&lon=" + lon + "&exclude=hourly,minutely" +"&appid=" + apiKey);

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONArray jsonArr = (JSONArray) jsonObject.get("daily");





            for(int i=0; i<jsonArr.size(); i++) {
                dailyWeather = new DailyWeather();
                dailyWeather.setLat(lat);
                dailyWeather.setLon(lon);
                dailyWeather.setTimezone((String) jsonObject.get("timezone"));
                dailyWeather.setTimezone_offset((long) jsonObject.get("timezone_offset"));

                JSONObject daily = (JSONObject)jsonArr.get(i);
                JSONObject temp = (JSONObject) daily.get("temp");
                JSONObject feels_like = (JSONObject) daily.get("feels_like");
                JSONArray dailyWeatherArray = (JSONArray) daily.get("weather");
                JSONObject dailyWeatherData = (JSONObject)dailyWeatherArray.get(0);

                dailyWeather.setDt(dailyWeather.changeUnixTime(daily.get("dt").toString()));
                dailyWeather.setSunrise(dailyWeather.changeUnixTime(daily.get("sunrise").toString()));
                dailyWeather.setSunset(dailyWeather.changeUnixTime(daily.get("sunset").toString()));
                dailyWeather.setMoonrise(dailyWeather.changeUnixTime(daily.get("moonrise").toString()));
                dailyWeather.setMoonset(dailyWeather.changeUnixTime(daily.get("moonset").toString()));
                dailyWeather.setMoon_phase(Float.parseFloat(daily.get("moon_phase").toString()));  //(float) daily.get("moon_phase")
                dailyWeather.setPressure((long) daily.get("pressure"));
                dailyWeather.setHumidity(Float.parseFloat(daily.get("humidity").toString()));
                dailyWeather.setDew_point(Float.parseFloat(daily.get("dew_point").toString())-273.15f);
                dailyWeather.setUvi(Float.parseFloat(daily.get("uvi").toString()));
                dailyWeather.setClouds(Integer.parseInt(daily.get("clouds").toString()));
                dailyWeather.setWind_speed(Float.parseFloat(daily.get("wind_speed").toString()));
                dailyWeather.setWind_deg(Integer.parseInt(daily.get("wind_deg").toString()));
                dailyWeather.setWind_gust(Float.parseFloat(daily.get("wind_gust").toString()));

                dailyWeather.setTemp_day(Float.parseFloat(temp.get("day").toString())-273.15f);
                dailyWeather.setTemp_min(Float.parseFloat(temp.get("min").toString())-273.15f);
                dailyWeather.setTemp_max(Float.parseFloat(temp.get("max").toString())-273.15f);
                dailyWeather.setTemp_night(Float.parseFloat(temp.get("night").toString())-273.15f);
                dailyWeather.setTemp_eve(Float.parseFloat(temp.get("eve").toString())-273.15f);
                dailyWeather.setTemp_morn(Float.parseFloat(temp.get("morn").toString())-273.15f);

                dailyWeather.setFeelsLike_day(Float.parseFloat(feels_like.get("day").toString())-273.15f);
                dailyWeather.setFeelsLike_night(Float.parseFloat(feels_like.get("night").toString())-273.15f);
                dailyWeather.setFeelsLike_eve(Float.parseFloat(feels_like.get("eve").toString())-273.15f);
                dailyWeather.setFeelsLike_morn(Float.parseFloat(feels_like.get("morn").toString())-273.15f);


                dailyWeather.setWeather_id(Integer.parseInt(dailyWeatherData.get(("id")).toString()));
                dailyWeather.setWeather_main((String) dailyWeatherData.get(("main")));
                dailyWeather.setWeather_description((String) dailyWeatherData.get(("description")));
                dailyWeather.setWeather_icon((String) dailyWeatherData.get(("icon")));

                dailyWeathers.add(dailyWeather);

            }

        return dailyWeathers;
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