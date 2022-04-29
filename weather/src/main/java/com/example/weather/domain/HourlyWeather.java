package com.example.weather.domain;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HourlyWeather  {
    private String weather;

    private String timezone;

    private String dt;

    public String changeUnixTime (String timeStampStr) {
        long timeStamp = Long.parseLong(timeStampStr);
        Date date = new Date(timeStamp * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String formattedDate = sdf.format(date);

        return formattedDate;
    }
}