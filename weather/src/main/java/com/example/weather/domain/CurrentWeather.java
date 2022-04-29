package com.example.weather.domain;


import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CurrentWeather {

    private float lat;

    private float lon;

    private String timezone;

    private long timezone_offset;

    private String current_dt;

    private String current_sunrise;

    private String current_sunset;

    private float current_temp;

    private float current_feels_like;

    private long current_pressure;

    private float current_humidity;

    private float current_dew_point;

    private float current_uvi;

    private int current_clouds;

    private int current_visibility;

    private float current_wind_speed;

    private int current_wind_deg;

    private float current_wind_gust;

    private int weather_id;

    private String weather_main;

    private String weather_description;

    private String weather_icon;



    public String changeUnixTime (String timeStampStr) {
        long timeStamp = Long.parseLong(timeStampStr);
        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

}









//
//@Data
//public class CurrentWeather {
//
//    private float lat;
//
//    private float lon;
//
//    private String timezone;
//
//    private long timezone_offset;
//
//    private Current current;
//
//    private Daily daily;
//
//    @Data
//
//    public static class Current {
//        private long dt;
//        private long sunrise;
//        private long sunset;
//        private float temp;
//        private float feels_like;
//        private long pressure;
//        private float humidity;
//        private float dew_point;
//        private float uvi;
//        private int clouds;
//        private int visibility;
//        private float wind_speed;
//        private int wind_deg;
//        private float wind_gust;
//        private Weather weather;
//    }
//
//    @Data
//    public static class Daily {
//        private long dt;
//        private long sunrise;
//        private long sunset;
//        private long moonrise;
//        private long moonset;
//        private float moon_phase;
//        private Temp temp;
//        private Feelslike feels_like;
//        private long pressure;
//        private float humidity;
//        private float dew_point;
//        private float wind_speed;
//        private int wind_deg;
//        private float wind_gust;
//        private Weather weather;
//        private int clouds;
//        private int pop;
//        private float rain;
//        private float uvi;
//
//    }
//    @Data
//    public static class Feelslike {
//        private float day;
//        private float night;
//        private float eve;
//        private float morn;
//    }
//    @Data
//    public static class Temp {
//        private float day;
//        private float min;
//        private float max;
//        private float night;
//        private float eve;
//        private float morn;
//    }
//    /*@Data
//    public static class Hourly {
//        private long dt;
//        private float temp;
//        private float feels_like;
//        private long pressure;
//        private float humidity;
//        private float dew_point;
//        private float uvi;
//        private int clouds;
//        private int visibility;
//        private float wind_speed;
//        private int wind_deg;
//        private float wind_gust;
//        private Weather weather;
//    }*/
//
//    @Data
//    public static class Weather{
//        private int id;
//        private String main;
//        private String description;
//        private String icon;
//    }
//
//    @Data
//    public static class Rain {
//
//        /** 지난 1 시간 동안의 강우량, mm */
//        @JsonProperty("1h")
//        private float rain1h;
//
////        /** 지난 3 시간 동안의 강우량, mm */
////        @JsonProperty("3h")
////        private float rain3h;
//    }
//
//}
