package com.example.weather.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DailyWeather {

    private String dt;

    //private Temp temp;

    // Feelslike feels_like;

    private float humidity;

    private float wind_speed;

    //private Weather weather;


    private float rain;

    private float uvi;

    private float temp_day;

    private float temp_min;

    private float temp_max;

    private String weather_main;

  //점수 score 추가

    public String changeUnixTime (String timeStampStr) {
        long timeStamp = Long.parseLong(timeStampStr);
        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

}
