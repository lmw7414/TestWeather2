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


    private float lat;

    private float lon;

    private String timezone;

    private long timezone_offset;

    private String dt;

    private String sunrise;

    private String sunset;

    private String moonrise;

    private String moonset;

    private float moon_phase;

    //private Temp temp;

    // Feelslike feels_like;

    private long pressure;

    private float humidity;

    private float dew_point;  //

    private float wind_speed;

    private int wind_deg;

    private float wind_gust;

    //private Weather weather;

    private int clouds;

    private int pop;

    private float rain;

    private float uvi;

    private float feelsLike_day;

    private float feelsLike_night;

    private float feelsLike_eve;

    private float feelsLike_morn;

    private float temp_day;

    private float temp_min;

    private float temp_max;

    private float temp_night;

    private float temp_eve;

    private float temp_morn;

    private int weather_id;

    private String weather_main;

    private String weather_description;

    private String weather_icon;


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
