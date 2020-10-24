package xyz.acproject.danmuji.entity.other;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getFx() {
        if(StringUtils.isEmpty(fx)){
            return getFengxiang();
        }
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getFl() {
        if(StringUtils.isEmpty(fl)){
            return getFengli();
        }
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFengxiang() {
        if(StringUtils.isEmpty(fengxiang)){
            return getFx();
        }
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengli() {
        if(StringUtils.isEmpty(fengli)){
            return getFl();
        }
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", fx='" + fx + '\'' +
                ", low='" + low + '\'' +
                ", fl='" + fl + '\'' +
                ", type='" + type + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", fengli='" + fengli + '\'' +
                ", ganmao='" + ganmao + '\'' +
                ", wendu='" + wendu + '\'' +
                '}';
    }
}
