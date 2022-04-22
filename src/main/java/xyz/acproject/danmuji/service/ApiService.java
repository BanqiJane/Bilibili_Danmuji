package xyz.acproject.danmuji.service;


import xyz.acproject.danmuji.entity.other.Weather;

public interface ApiService {

    Weather getWeather(String city, Short day);
}
