package xyz.acproject.danmuji.service;


import xyz.acproject.danmuji.entity.Weather.Weather;
import xyz.acproject.danmuji.entity.Weather.WeatherV2;
import xyz.acproject.danmuji.entity.apex.ApexMessage;
import xyz.acproject.danmuji.entity.apex.PredatorResult;

public interface ApiService {

    Weather getWeather(String city, Short day);

    WeatherV2 getWeatherV2(String city, Short day);

    PredatorResult getApexPredator(String key, String type);

    ApexMessage getApexMessage();
}
