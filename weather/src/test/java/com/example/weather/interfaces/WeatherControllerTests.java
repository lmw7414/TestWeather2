package com.example.weather.interfaces;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

class WeatherControllerTests {

    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/onecall";

    private final String apiKey = "7e794e5e8d90a420c85cddb7aeb9358e"; // 발급받은 API key

    @Test
    void getWeather() {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        String result = "";
        try {

            double lat = 35;
            double lon = 128.5;

            URL url = new URL(BASE_URL + "?lat=" + lat + "&lon=" + lon + "&exclude=hourly,minutely" +"&appid=" + apiKey);

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();
            System.out.println(result);


            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONObject current = (JSONObject)jsonObject.get("current");
            JSONArray currentWeather = (JSONArray) current.get("weather");
            JSONObject currentWeatherData = (JSONObject)currentWeather.get(0);
            JSONArray jsonArr = (JSONArray) jsonObject.get("daily");

            System.out.println(jsonObject.get("timezone"));

            System.out.println(current.get("dt"));
            System.out.println(current.get("sunrise"));
            System.out.println(current.get("sunset"));
            System.out.println(current.get("temp"));
            System.out.println(current.get("feels_like"));
            System.out.println(current.get("pressure"));
            System.out.println(current.get("humidity"));
            System.out.println(current.get("dew_point"));
            System.out.println(current.get("uvi"));
            System.out.println(current.get("clouds"));
            System.out.println(current.get("visibility"));
            System.out.println(current.get("wind_speed"));
            System.out.println(current.get("wind_deg"));
            System.out.println(current.get("wind_gust"));
            System.out.println(currentWeatherData.get("id"));
            System.out.println(currentWeatherData.get("main"));
            System.out.println(currentWeatherData.get("description"));
            System.out.println(currentWeatherData.get("icon"));

            for(int i=0; i<jsonArr.size(); i++) {

                JSONObject daily = (JSONObject)jsonArr.get(i);
                System.out.println(daily.get("dt"));
                System.out.println(daily.get("sunrise"));
                System.out.println(daily.get("sunset"));
                System.out.println(daily.get("moonrise"));
                System.out.println(daily.get("moonset"));
                System.out.println(daily.get("moon_phase"));

                JSONObject temp = (JSONObject) daily.get("temp");
                System.out.println(temp.get("day"));
                System.out.println(temp.get("min"));
                System.out.println(temp.get("max"));
                System.out.println(temp.get("night"));
                System.out.println(temp.get("eve"));
                System.out.println(temp.get("morn"));
                JSONObject feels_like = (JSONObject) daily.get("feels_like");
                System.out.println(feels_like.get("day"));
                System.out.println(feels_like.get("night"));
                System.out.println(feels_like.get("eve"));
                System.out.println(feels_like.get("morn"));
                System.out.println(daily.get("pressure"));
                System.out.println(daily.get("humidity"));
                System.out.println(daily.get("dew_point"));
                System.out.println(daily.get("wind_speed"));
                System.out.println(daily.get("wind_deg"));
                System.out.println(daily.get("wind_gust"));
                System.out.println(daily.get("uvi"));
                System.out.println(daily.get("clouds"));
                System.out.println(daily.get("pop"));

                JSONArray dailyWeather = (JSONArray) daily.get("weather");
                JSONObject dailyWeatherData = (JSONObject)dailyWeather.get(0);
                System.out.println(dailyWeatherData.get("id"));
                System.out.println(dailyWeatherData.get("main"));
                System.out.println(dailyWeatherData.get("description"));
                System.out.println(dailyWeatherData.get("icon"));

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

//https://web-obj.tistory.com/182
//https://java.tutorialink.com/org-json-simple-jsonarray-cannot-be-cast-to-class-org-json-simple-jsonobject/

//https://velog.io/@garam0410/Java-OPEN-API-%ED%8C%8C%EC%8B%B1%ED%95%98%EA%B8%B0-JSON