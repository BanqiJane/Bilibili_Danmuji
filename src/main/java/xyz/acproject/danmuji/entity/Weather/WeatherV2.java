package xyz.acproject.danmuji.entity.Weather;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import xyz.acproject.danmuji.utils.JodaTimeUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Admin
 * @ClassName WeatherV2
 * @Description TODO
 * @date 2023/1/19 15:28
 * @Copyright:2023
 */
@Data
public class WeatherV2 {
    private String city;
    private String date;
    private String date_parse;

    private String week_name;
    private String fx;
    private String fx_day;
    private String fx_night;
    private String fl;
    private String fl_day;
    private String fl_night;

    private String type;
    private String type_day;
    private String type_night;

    private String sunriseTime;

    private String sunsetTime;

    private String wendu_day;

    private String wendu_night;

    private String wendu_low;

    private String wendu_high;

    private String wendu_range;

    private String wendu;

    private String shidu;

    private Map<String, String> tipMap;

    private String tip;

    public String tipParse(String tipName){
        StringBuilder tipChildBuilder = new StringBuilder();
        tipChildBuilder.append(tipName);
        if(!CollectionUtils.isEmpty(tipMap)){
            String value = tipMap.get(tipName);
            if(StringUtils.isNotBlank(value)){
                tipChildBuilder.append(value);
            }else{
                tipChildBuilder.append("暂无");
            }
        }else{
            tipChildBuilder.append("暂无");
        }
        return tipChildBuilder.toString();
    }
}
