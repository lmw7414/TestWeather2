//package com.example.weather.application;
//
//import com.example.weather.WeatherApplication;
//import com.example.weather.domain.DailyWeather;
//import com.example.weather.domain.DailyWeatherRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class WeatherService {
//
//    private DailyWeatherRepository dailyWeatherRepository;
//
//    @Autowired
//    public WeatherService(DailyWeatherRepository dailyWeatherRepository) {
//         this.dailyWeatherRepository = dailyWeatherRepository;
//    }
//
//    public List<DailyWeather> getDailyWeather() {
//        List<DailyWeather> dailyWeathers = dailyWeatherRepository.findAll();
//        return dailyWeathers;
//    }
//
//}
