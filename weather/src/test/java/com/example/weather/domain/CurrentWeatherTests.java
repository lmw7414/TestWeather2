package com.example.weather.domain;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

class CurrentWeatherTests {

    @Test
    public void changeUnixTime() {
        long timeStamp = Long.parseLong("1648695600");
        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String formattedDate = sdf.format(date);

        System.out.println(formattedDate);
    }


    @Test
    public void getGeoDataByAddress() {

        //public static float[] getGeoDataByAddress(String completeAddress) {

        String completeAddress = "대구";
        float[] arr = new float[2];
        String result = "";
        try {
            String API_KEY = "AIzaSyDmc0I-f4BJedfRyA6jJuSBX8JuVRpPT1g";
            String surl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(completeAddress, "UTF-8") + "&key=" + API_KEY;

            StringBuilder responseStrBuilder = new StringBuilder();


            URL url = new URL(surl);
            //System.out.println(url);

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            while ((result = bf.readLine()) != null) {
                //System.out.println(">>>>>>>>>>     "+result);
                responseStrBuilder.append(result);
            }

            //System.out.println(responseStrBuilder);

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
            System.out.println("lat " + arr[0]);
            System.out.println("lon " + arr[1]);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}