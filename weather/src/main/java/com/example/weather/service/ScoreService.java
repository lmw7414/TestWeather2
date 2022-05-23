package com.example.weather.service;

import com.example.weather.domain.DailyWeather;
import com.example.weather.domain.ScoreFilter;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreService {

    public List<Float> calScore(String city, String weather, int wind) throws IOException, ParseException {

        WeatherService weatherService = new WeatherService();
        List<Float> scoreset = new ArrayList<>();
        List<DailyWeather> dailyWeathers = weatherService.getDailyWeatherData(city);

        int clearScore;  // 구름 없음
        int cloudyScore;  // 구름 있음
        int snowScore;
        int rainScore;

        int windScore0;
        int windScore1;
        int windScore2;
        int windScore3;


        float result1;
        float result2;
        float result3;
        switch (weather) {
            case "clear":
                clearScore = 60;
                cloudyScore = 40;
                snowScore = 0;
                rainScore = 0;
                break;
            case "bitCloudy":
                clearScore = 50;
                cloudyScore = 50;
                snowScore = 0;
                rainScore = 0;
                break;
            case "cloudy":
                clearScore = 40;
                cloudyScore = 60;
                snowScore = 0;
                rainScore = 0;
                break;
            case "snow":

                clearScore = 0;
                cloudyScore = 0;
                snowScore = 100;
                rainScore = 0;
                break;
            case "rain":
                clearScore = 0;
                cloudyScore = 0;
                snowScore = 0;
                rainScore = 100;
                break;
            default:
                clearScore = 0;
                cloudyScore = 0;
                snowScore = 0;
                rainScore = 0;
                break;
        }

        switch (wind)
        {
            case 0:
                windScore0 = 50;
                windScore1 = 35;
                windScore2 = 10;
                windScore3 = 5;
                break;
            case 1:
                windScore0 = 25;
                windScore1 = 50;
                windScore2 = 20;
                windScore3 = 5;
                break;
            case 2:
                windScore0 = 15;
                windScore1 = 25;
                windScore2 = 40;
                windScore3 = 20;
                break;
            case 3:
                windScore0 = 5;
                windScore1 = 10;
                windScore2 = 35;
                windScore3 = 50;
                break;
            default:
                windScore0 = 0;
                windScore1 = 0;
                windScore2 = 0;
                windScore3 = 0;
                break;
        }

        for( int i=0; i< dailyWeathers.size(); i++) {

            switch(dailyWeathers.get(i).getWeather_main()) {
                case "Clear":
                    result1 = (float)(33.3 * clearScore / 100);
                    break;
                case "Clouds":
                    result1 = (float)(33.3 * cloudyScore / 100);
                    break;
                case "Rain":
                    result1 = (float)(33.3 * rainScore / 100);
                    break;
                case "Snow":
                    result1 = (float)(33.3 * snowScore / 100);
                    break;
                default:
                    result1 = 0;
                    break;
            }
            float wind_speed = dailyWeathers.get(i).getWind_speed();
            if(wind_speed < 4)
                result2 = (float)(33.3 * windScore0 / 100);
            else if(wind_speed <9)
                result2 = (float)(33.3 * windScore1 / 100);
            else if(wind_speed <14)
                result2 = (float)(33.3 * windScore2 / 100);
            else
                result2 = (float)(33.3 * windScore3 / 100);


            float di =dailyWeathers.get(i).getScore();
            if(di < 69)
                result3 = (float)(33.3 * 0.4);
            else if(di < 71)
                result3 = (float)(33.3 * 0.35);
            else if(di < 76)
                result3 = (float)(33.3 * 0.15);
            else if(di < 81)
                result3 = (float)(33.3 * 0.05);
            else if(di < 84)
                result3 = (float)(33.3 * 0.03);
            else
                result3 = (float)(33.3 * 0.02);

            scoreset.add(result1 + result2 + result3);
        }


        return scoreset;
    }
}
