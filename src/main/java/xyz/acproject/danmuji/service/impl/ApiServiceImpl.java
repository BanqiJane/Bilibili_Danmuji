package xyz.acproject.danmuji.service.impl;

import org.springframework.stereotype.Service;
import xyz.acproject.danmuji.entity.Weather.Weather;
import xyz.acproject.danmuji.entity.Weather.WeatherV2;
import xyz.acproject.danmuji.entity.apex.ApexMessage;
import xyz.acproject.danmuji.entity.apex.PredatorResult;
import xyz.acproject.danmuji.http.HttpOtherData;
import xyz.acproject.danmuji.service.ApiService;
import xyz.acproject.danmuji.utils.XmlUtils;

import java.util.List;
import java.util.Map;

@Service
public class ApiServiceImpl implements ApiService {


    @Override
    public Weather getWeather(String city, Short day) {
        Weather weather = null;
        Map<String, List<Weather>> weatherMaps = HttpOtherData.httpGetweather(city);
        if (null != weatherMaps) {
            List<Weather> oldWeathers = weatherMaps.get("old");
            List<Weather> newWeathers = weatherMaps.get("new");
            switch (day) {
                case -1:
                    if (null != oldWeathers && oldWeathers.size() > 0)
                        weather = oldWeathers.get(0);
                    break;
                case 0:
                    if (null != newWeathers && newWeathers.size() > 0)
                        weather = newWeathers.get(0);
                    break;
                case 1:
                    if (null != newWeathers && newWeathers.size() > 0)
                        weather = newWeathers.get(1);
                    break;
                case 2:
                    if (null != newWeathers && newWeathers.size() > 0)
                        weather = newWeathers.get(2);
                    break;
                case 3:
                    if (null != newWeathers && newWeathers.size() > 0)
                        weather = newWeathers.get(3);
                    break;
                case 4:
                    if (null != newWeathers && newWeathers.size() > 0)
                        weather = newWeathers.get(4);
                    break;
                default:
                    break;
            }
        }
        if (null != weather) {
            weather.setFl(XmlUtils.getData(weather.getFl(), String.class));
        }
        return weather;
    }

    public WeatherV2 getWeatherV2(String city, Short day) {
        WeatherV2 weatherV2 = null;
        Map<String, WeatherV2> weatherV2Maps = HttpOtherData.httpGetWeatherV2(city);
        if (null != weatherV2Maps) {
            weatherV2 = weatherV2Maps.get(day+"");
        }
        return weatherV2;
    }

    public PredatorResult getApexPredator(String key, String type) {
        PredatorResult predatorResult=null;
        predatorResult = HttpOtherData.httpGetApexPredator(key, type);
        return predatorResult;
    }
    public ApexMessage getApexMessage() {
        ApexMessage apexMessage=null;
        apexMessage = HttpOtherData.httpGetApexMessage();
        return apexMessage;
    }
}
