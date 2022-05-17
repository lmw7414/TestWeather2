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
import java.util.ArrayList;
import java.util.List;

@RestController
public class DustController {

    private final String BASE_URL = "http://api.openweathermap.org/data/2.5/air_pollution/history";
    private final String apiKey = "7e794e5e8d90a420c85cddb7aeb9358e"; // 발급받은 API key

    @GetMapping("/weathers/dust/{lat}/{lon}/{start}/{end}")
    public List<DailyDust> getDust(@PathVariable("lat") float lat,
                                   @PathVariable("lon") float lon, @PathVariable("start") int start, @PathVariable("end") int end)
            throws IOException, ParseException {

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

}


