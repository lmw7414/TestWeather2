package com.example.weather.interfaces;

import com.example.weather.domain.DailyDust;
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

    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/air_pollution/history";
    private final String apiKey = "7e794e5e8d90a420c85cddb7aeb9358e"; // 발급받은 API key

    @GetMapping("/weathers/dust/{city}/{start}/{end}")
    public List<DailyDust> getDust(@PathVariable("city") String city,
                                    @PathVariable("start") int start,
                                   @PathVariable("end") int end)
            throws IOException, ParseException {

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


