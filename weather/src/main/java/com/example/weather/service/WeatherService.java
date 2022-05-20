package com.example.weather.service;

import com.example.weather.domain.CurrentWeather;
import com.example.weather.domain.DailyDust;
import com.example.weather.domain.DailyWeather;
import com.example.weather.domain.HourlyWeather;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/onecall";
    private final String apiKey = "7e794e5e8d90a420c85cddb7aeb9358e"; // 발급받은 API key


    //DailyWeather Data
    public List<DailyWeather> getDailyWeatherData(String city)
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
    //CurrentWeather Data
    public CurrentWeather getCurrentWeatherData(@PathVariable("city") String city)
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
        //currentWeather.setCurrent_wind_gust(Float.parseFloat(currentObject.get("wind_gust").toString()));
        currentWeather.setWeather_id(Integer.parseInt(currentWeatherData.get("id").toString()));
        currentWeather.setWeather_main((String) currentWeatherData.get(("main")));
        currentWeather.setWeather_description((String) currentWeatherData.get(("description")));
        currentWeather.setWeather_icon((String) currentWeatherData.get(("icon")));

        return currentWeather;
    }
    //HourlyWeather Data
    public List<HourlyWeather> getHourlyWeatherData(String city) throws IOException, ParseException {

        float arr[] = getGeoDataByAddress(city);

        float lat = (float)arr[0];
        float lon = (float)arr[1];

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
            hourlyWeather.setTemp(Float.parseFloat(hourly.get("temp").toString())-273.15f);
            hourlyWeather.setWeather_id(Integer.parseInt(hourlyWeatherData.get(("id")).toString()));
            hourlyWeather.setWeather_main((String) hourlyWeatherData.get(("main")));
            hourlyWeather.setWeather_description((String) hourlyWeatherData.get(("description")));
            hourlyWeather.setWeather_icon((String) hourlyWeatherData.get(("icon")));


            hourlyWeathers.add(hourlyWeather);

        }

        return hourlyWeathers;
    }
    //Dust Data
    public List<DailyDust> getDust(String city,
                                   int start,
                                   int end)
            throws IOException, ParseException {

        String BASE_URL = "http://api.openweathermap.org/data/2.5/air_pollution/history";

        float arr[] = getGeoDataByAddress(city);

        float lat = (float)arr[0];
        float lon = (float)arr[1];

        String result = "";
        DailyDust dailyDust;
        Double tempDouble;
        Long tempLong;
        int count = 0;

        URL url = new URL(BASE_URL + "?lat=" + lat + "&lon=" + lon + "&start=" + start+ "&end=" + end +"&appid=" + apiKey);

        BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = bf.readLine();

        List<DailyDust> dailyDusts = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONArray jsonArr = (JSONArray) jsonObject.get("list");



        for(int i=0; i<jsonArr.size(); i++) {
            dailyDust = new DailyDust();

            JSONObject dust = (JSONObject)jsonArr.get(i);
            JSONObject pm = (JSONObject) dust.get("components");

            if (count % 24 == 0) {
                dailyDust.setDt(Long.toString((Long) dust.get("dt")));
                if (pm.get("pm2_5").getClass().getName() == "java.lang.Double") {
                    tempDouble = (Double) pm.get("pm2_5");
                    dailyDust.setPm(tempDouble);
                } else if (pm.get("pm2_5").getClass().getName() == "java.lang.Long") {
                    tempLong = (Long) pm.get("pm2_5");
                    dailyDust.setPm((double) tempLong);
                }
                dailyDusts.add(dailyDust);
            }
            count++;
        }
        return dailyDusts;
    }



    // geo api 위도 경도 반환
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
