package xyz.acproject.danmuji.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.Weather.WeatherV2;
import xyz.acproject.danmuji.entity.apex.ApexMessage;
import xyz.acproject.danmuji.entity.apex.PredatorResult;
import xyz.acproject.danmuji.entity.heart.XData;
import xyz.acproject.danmuji.entity.Weather.Weather;
import xyz.acproject.danmuji.utils.OkHttp3Utils;

import java.util.*;

/**
 * @author BanqiJane
 * @ClassName HttpOtherData
 * @Description TODO
 * @date 2020年8月10日 下午12:28:55
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class HttpOtherData {
    private static Logger LOGGER = LogManager.getLogger(HttpOtherData.class);

    public static String httpGetNewEdition() {
        String data = null;
        JSONObject jsonObject = null;
        String edition = null;
        String code = "-1";
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        datas = new HashMap<>(4);
        datas.put("roomid", PublicDataConf.centerSetConf.getRoomid().toString());
        datas.put("edition", PublicDataConf.EDITION);
        datas.put("time", String.valueOf(System.currentTimeMillis()));
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://bilibili.acproject.xyz/getEdition", headers, datas)
                    .body().string();
            if (data == null)
                return edition;
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                edition = ((JSONObject) jsonObject.get("result")).getString("value");
                PublicDataConf.NEW_EDITION = edition;
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            edition = "获取公告失败";
            LOGGER.error("请求服务器超时，获取最新版本失败");
            data = null;
        }
        return edition;
    }

    public static String httpGetNewAnnounce() {
        String data = null;
        JSONObject jsonObject = null;
        String announce = null;
        String code = "-1";
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        datas = new HashMap<>(4);
        datas.put("roomid", PublicDataConf.centerSetConf.getRoomid().toString());
        datas.put("edition", PublicDataConf.EDITION);
        datas.put("time", String.valueOf(System.currentTimeMillis()));
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://bilibili.acproject.xyz/getAnnounce", headers, datas)
                    .body().string();
            if (data == null)
                return announce;
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                announce = ((JSONObject) jsonObject.get("result")).getString("value");
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            announce = "获取最新公告失败";
            LOGGER.error("请求服务器超时，获取最新公告失败");
            data = null;
        }
        return announce;
    }

    public static Long httpGetClockInRecord() {
        String data = null;
        JSONObject jsonObject = null;
        Long uid = null;
        String code = "-1";
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        datas = new HashMap<>(4);
        datas.put("uid", PublicDataConf.USER.getUid().toString());
        datas.put("edition", PublicDataConf.EDITION);
        datas.put("time", String.valueOf(System.currentTimeMillis()));
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://bilibili.acproject.xyz/getClockRecord", headers, datas)
                    .body().string();
            if (data == null)
                return null;
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                if (jsonObject.get("result") != null) {
                    uid = ((JSONObject) jsonObject.get("result")).getLong("uid");
                }
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            uid = null;
            LOGGER.error("请求服务器超时，获取最新打卡记录失败");
            data = null;
        }
        return uid;
    }

    public static Long httpPOSTSetClockInRecord() {
        String data = null;
        JSONObject jsonObject = null;
        Long uid = null;
        String code = "-1";
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        datas = new HashMap<>(4);
        datas.put("uid", PublicDataConf.USER.getUid().toString());
        datas.put("edition", PublicDataConf.EDITION);
        datas.put("time", String.valueOf(System.currentTimeMillis()));
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpPostForm("http://bilibili.acproject.xyz/setClockRecord", headers, datas)
                    .body().string();
            if (data == null)
                return null;
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                if (jsonObject.get("result") != null) {
                    uid = ((JSONObject) jsonObject.get("result")).getLong("uid");
                }
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            uid = null;
            LOGGER.error("请求服务器超时，获取最新打卡记录失败");
            data = null;
        }

        return uid;
    }


    @Deprecated
    public static String httpGetIp() {
        String data = null;
        JSONObject jsonObject = null;
        String status = null;
        String ip = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://ip-api.com/json/", headers, null)
                    .body().string();
            if (data == null)
                return null;
            jsonObject = JSONObject.parseObject(data);
            try {
                status = jsonObject.getString("status");
            } catch (Exception e) {
                // TODO: handle exception
            }
            if (StringUtils.isBlank(status)) {
                return "获取失败:请自行获取本机对公Ip地址";
            }
            if (status.equals("success")) {
                ip = jsonObject.getString("query");
            } else {
                LOGGER.error("获取ip失败" + jsonObject.toString());
            }
        } catch (Exception e) {
            ip = "获取失败:请自行获取本机对公Ip地址";
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            LOGGER.error(ip);
            data = null;
        }
        return ip;
    }

    public static String httpGetIpV2() {
        String data = "";
        JSONObject jsonObject = null;
        String code = "-1";
        PredatorResult predatorResult = null;
        Map<String, String> headers = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://bilibili.acproject.xyz/ip", headers, null)
                    .body().string();
            if (data == null)
                return "";
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                data = jsonObject.getString("result");
            } else {
                LOGGER.error("ip获取： 未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            LOGGER.error("请求服务器超时，获取ip失败");
            data = "";
        }
        return data;
    }


    public static String httpPostEncsUrl() {
        String data = null;
        JSONObject jsonObject = null;
        String url = null;
        String code = "-1";
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        datas = new HashMap<>(4);
        datas.put("roomid", PublicDataConf.centerSetConf.getRoomid().toString());
        datas.put("edition", PublicDataConf.EDITION);
        datas.put("time", String.valueOf(System.currentTimeMillis()));
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpPostForm("http://bilibili.acproject.xyz/getEncsServer", headers, datas)
                    .body().string();
            if (data == null)
                return url;
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                url = jsonObject.getString("result");
                PublicDataConf.SMALLHEART_ADRESS = url;
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            url = null;
            LOGGER.error("请求服务器超时，获取服务器链接失败");
            data = null;
        }

        return url;
    }

    /**
     * 加密s函数方法来自 https://github.com/lkeme/bilibili-pcheartbeat
     * 服务器来自 https://github.com/lkeme/BiliHelper-personal
     *
     * @param xData
     * @param ts
     * @return
     */
    public static String httpPostencS(XData xData, long ts) {
        String data = null;
        JSONObject jsonObject = null;
        String s = null;
        String url = PublicDataConf.SMALLHEART_ADRESS;
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        Map<String, String> headers = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        JSONObject t = new JSONObject();
        t.put("id", xData.getId());
        t.put("device", xData.getDevice());
        t.put("ets", xData.getEts());
        t.put("benchmark", xData.getBenchmark());
        t.put("time", xData.getTime());
        t.put("ts", ts);
        t.put("ua", xData.getUa());
        JSONObject json = new JSONObject();
        json.put("t", t);
        json.put("r", xData.getSecret_rule());
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        try {
            data = OkHttp3Utils.getHttp3Utils().httpPostJson(url, headers, json.toJSONString()).body().string();
            if (data == null)
                return null;
            jsonObject = JSONObject.parseObject(data);
            try {
                s = jsonObject.getString("s");
            } catch (Exception e) {
                LOGGER.error("加密s错误");
                // TODO: handle exception
                s = null;
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error("连接至加密服务器错误？不存在");
            data = null;
            s = null;
//			e.printStackTrace();
        }

        return s;
    }

    //隐私版本 移除了天气功能调用服务器
    @Deprecated
    public static Weather httpPostWeather(String city, Short day) {
        String data = null;
        JSONObject jsonObject = null;
        String code = "-1";
        Weather weather = null;
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        datas = new HashMap<>(3);
        datas.put("city", city);
        datas.put("day", day.toString());
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpPostForm("http://bilibili.acproject.xyz/getWeather", headers, datas)
                    .body().string();
            if (data == null)
                return null;
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                weather = JSONObject.parseObject(jsonObject.getString("result"), Weather.class);
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            LOGGER.error("请求服务器超时，获取服务器链接失败");
            data = null;
        }
        return weather;
    }

    // 天气接口http://wthrcdn.etouch.cn/weather_mini?city=北京  备用
    /* 天气接口已废弃  */
    @Deprecated
    public static Map<String, List<Weather>> httpGetweather(String city) {
        String data = null;
        JSONObject jsonObject = null;
        short code = -1;
        Map<String, String> headers = null;
        headers = new HashMap<>(2);
        Map<String, List<Weather>> weathers = null;
        String cname = null;
        String ganmao = null;
        String wendu = null;
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        try {
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://wthrcdn.etouch.cn/weather_mini?city=" + city, headers, null)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            LOGGER.error("请求服务器超时，获取天气失败");
            data = null;
        }
        if (data == null)
            return null;
        try {
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getShort("status");
            if (code == 1000) {
                weathers = new HashMap<String, List<Weather>>();
                cname = ((JSONObject) jsonObject.get("data")).getString("city");
                ganmao = ((JSONObject) jsonObject.get("data")).getString("ganmao");
                wendu = ((JSONObject) jsonObject.get("data")).getString("wendu");
                List<Weather> oldWeathers = new ArrayList<>();
                Weather oldWeather = JSONObject.parseObject(((JSONObject) jsonObject.get("data")).getString("yesterday"), Weather.class);
                oldWeather.setCity(cname);
                oldWeather.setGanmao(ganmao);
                oldWeather.setWendu(wendu);
                oldWeathers.add(oldWeather);
                weathers.put("old", oldWeathers);
                List<Weather> newWeathers = JSONArray.parseArray(((JSONObject) jsonObject.get("data")).getString("forecast"), Weather.class);
                weathers.put("new", newWeathers);
                for (Iterator<Weather> iterator = newWeathers.iterator(); iterator.hasNext(); ) {
                    Weather weather = iterator.next();
                    weather.setWendu(wendu);
                    weather.setGanmao(ganmao);
                    weather.setCity(cname);
                }
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("desc"));
            }
        } catch (Exception e) {
            weathers = null;
        }
        return weathers;
    }

    public static Map<String, WeatherV2> httpGetWeatherV2(String city) {
        String data = null;
        JSONObject jsonObject = null;
        String code = "-200";
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        datas = new HashMap<>(5);
        Map<String, WeatherV2> weathers = null;
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        try {
            datas.put("edition", PublicDataConf.EDITION);
            datas.put("time", String.valueOf(System.currentTimeMillis()));
            datas.put("city", city);
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://bilibili.acproject.xyz/weather", headers, datas)
                    .body().string();
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            LOGGER.error("请求服务器超时，获取天气失败");
            data = null;
        }
        if (data == null)
            return null;
        try {
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                weathers = new HashMap<String, WeatherV2>();
                List<WeatherV2> weatherV2s = jsonObject.getJSONArray("result").toJavaList(WeatherV2.class);
                if (!CollectionUtils.isEmpty(weatherV2s)) {
                    int i = -1;
                    //遍历器循环
                    for (Iterator<WeatherV2> iterator = weatherV2s.iterator(); iterator.hasNext(); ) {
                        WeatherV2 weatherV2 = iterator.next();
                        weathers.put(i + "", weatherV2);
                        i++;
                    }
                }
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            weathers = null;
        }
        return weathers;
    }

    //apex 0PC排位大逃杀数据 1PC排位竞技场 3PS4大逃杀 4PS4竞技场

    public static PredatorResult httpGetApexPredator(String key, String type) {
        String data = null;
        JSONObject jsonObject = null;
        String code = "-1";
        PredatorResult predatorResult = null;
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        datas = new HashMap<>(5);
        if (StringUtils.isNotBlank(key)) {
            datas.put("key", key);
        }
        if (StringUtils.isNotBlank(type)) {
            datas.put("type", type);
        }
        try {
            datas.put("edition", PublicDataConf.EDITION);
            datas.put("time", String.valueOf(System.currentTimeMillis()));
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://bilibili.acproject.xyz/apex_banked", headers, datas)
                    .body().string();
            if (data == null)
                return predatorResult;
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                predatorResult = jsonObject.getObject("result", PredatorResult.class);
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            LOGGER.error("请求服务器超时，获取apex信息失败");
            data = null;
        }
        return predatorResult;
    }

    public static ApexMessage httpGetApexMessage() {
        String data = null;
        JSONObject jsonObject = null;
        String code = "-1";
        ApexMessage apexMessage = null;
        Map<String, String> headers = null;
        Map<String, String> datas = null;
        headers = new HashMap<>(2);
        headers.put("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        datas = new HashMap<>(3);
        try {
            datas.put("edition", PublicDataConf.EDITION);
            datas.put("time", String.valueOf(System.currentTimeMillis()));
            data = OkHttp3Utils.getHttp3Utils()
                    .httpGet("http://bilibili.acproject.xyz/apex_message", headers, datas)
                    .body().string();
            if (data == null)
                return apexMessage;
            jsonObject = JSONObject.parseObject(data);
            code = jsonObject.getString("code");
            if (code.equals("200")) {
                apexMessage = jsonObject.getObject("result", ApexMessage.class);
            } else {
                LOGGER.error("未知错误,原因:" + jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            LOGGER.error(e);
            LOGGER.error("请求服务器超时，获取apex总信息失败");
        }
        return apexMessage;
    }
}
