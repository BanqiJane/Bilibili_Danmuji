package xyz.acproject.danmuji.entity.Weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather implements Serializable {
    private static final long serialVersionUID = 7750191441583143032L;
    private String city;
    private String date;
    private String high;
    private String fx;
    private String low;
    private String fl;
    private String type;
    private String fengxiang;
    private String fengli;
    private String ganmao;
    private String wendu;

    public String getFx() {
        if(StringUtils.isEmpty(fx)){
            return getFengxiang();
        }
        return fx;
    }


    public String getFl() {
        if(StringUtils.isEmpty(fl)){
            return getFengli();
        }
        return fl;
    }


    public String getFengxiang() {
        if(StringUtils.isEmpty(fengxiang)){
            return getFx();
        }
        return fengxiang;
    }

    public String getFengli() {
        if(StringUtils.isEmpty(fengli)){
            return getFl();
        }
        return fengli;
    }

}
