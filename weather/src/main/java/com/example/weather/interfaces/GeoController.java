package com.example.weather.interfaces;

import com.example.weather.domain.GeoAddress;
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

@RestController
public class GeoController {
    private final String BASE_URL = "http://api.vworld.kr/req/address";
    private final String apiKey = "E450D748-4843-392D-9ABB-B886386D5747";

    @GetMapping("/weathers/geo/{lat}/{lon}")
    public GeoAddress getGeo (@PathVariable("lat") float lat,
                              @PathVariable("lon") float lon) throws IOException, ParseException{
        String point = "";
        String address = "";
        point = point.concat(String.valueOf(lon));
        point = point.concat((","));
        point = point.concat(String.valueOf(lat));
        GeoAddress geoAddress = new GeoAddress();

        URL url = new URL(BASE_URL + "?service=address&request=GetAddress&version=2.0&crs=epsg:4326&point="+ point + "&format=json&type=PARCEL&zipcode=false&simple=true&key=" + apiKey);
        String result = "";
//https://api.vworld.kr/req/address?service=address&request=GetAddress&version=2.0&crs=epsg:4326&point=128,35&format=json&type=PARCEL&zipcode=false&simple=true&key=E450D748-4843-392D-9ABB-B886386D5747


        BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        result = bf.readLine();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONArray jsonArr = (JSONArray) response.get("result");
        JSONObject structure = (JSONObject) jsonArr.get(0);
        structure = (JSONObject) structure.get("structure");
        address = (String) structure.get("level1");
        address = address.concat(" ");
        address = address.concat((String) structure.get("level2"));
        geoAddress.setAddress(address);

        return geoAddress;
    }
}
