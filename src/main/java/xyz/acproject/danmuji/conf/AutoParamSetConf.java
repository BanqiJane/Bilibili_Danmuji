package xyz.acproject.danmuji.conf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Admin
 * @ClassName AutoParamSetConf
 * @Description TODO
 * @date 2023/1/18 16:26
 * @Copyright:2023
 */
public class AutoParamSetConf {
    public static String[] weather_v1_all_params = new String[]{
            "%WEATHER%",
            "%W_CITY%",
            "%W_DATE%",
            "%H_WENDU%",
            "%L_WENDU%",
            "%WENDU%",
            "%W_FL%",
            "%W_FX%",
            "%W_TYPE%",
            "%W_TIPS%"
    };
    public static String[] weather_v1_params = new String[]{
            "%W_CITY%",
            "%W_DATE%",
            "%H_WENDU%",
            "%L_WENDU%",
            "%WENDU%",
            "%W_FL%",
            "%W_FX%",
            "%W_TYPE%",
            "%W_TIPS%"
    };

    public static String[] weather_v2_all_params = new String[]{
            "%WEATHER%",
            "%W_CITY%",
            "%W_DATE%",
            "%W_WEEK%",
            "%H_WENDU%",
            "%L_WENDU%",
            "%WENDU%",
            "%WENDU_RANGE%",
            "%W_FL%",
            "%W_FL_D%",
            "%W_FL_N%",
            "%W_FX%",
            "%W_FX_D%",
            "%W_FX_N%",
            "%W_TYPE%",
            "%W_TYPE_D%",
            "%W_TYPE_N%",
            "%SHIDU%",
            "%RICHU%",
            "%RILUO%",
            "%W_TIP%",
            "%W_TIP_XICHE%",
            "%W_TIP_CHUYOU%",
            "%W_TIP_HUAZHUANG%",
            "%W_TIP_CHUANYI%",
            "%W_TIP_GANMAO%"
    };
    public static String[] weather_v2_params = new String[]{
            "%W_CITY%",
            "%W_DATE%",
            "%W_WEEK%",
            "%H_WENDU%",
            "%L_WENDU%",
            "%WENDU%",
            "%WENDU_RANGE%",
            "%W_FL%",
            "%W_FL_D%",
            "%W_FL_N%",
            "%W_FX%",
            "%W_FX_D%",
            "%W_FX_N%",
            "%W_TYPE%",
            "%W_TYPE_D%",
            "%W_TYPE_N%",
            "%SHIDU%",
            "%RICHU%",
            "%RILUO%",
            "%W_TIP%",
            "%W_TIP_XICHE%",
            "%W_TIP_CHUYOU%",
            "%W_TIP_HUAZHUANG%",
            "%W_TIP_CHUANYI%",
            "%W_TIP_GANMAO%"
    };


    public static String[] apex_rank_params = new String[]{
            "%PC_RP_DFEN%",
            "%PC_RP_MTOTAL%",
            "%PC_AP_DFEN%",
            "%PC_AP_MTOTAL%",
            "%PS4_RP_DFEN%",
            "%PS4_RP_MTOTAL%",
            "%PS4_AP_DFEN%",
            "%PS4_AP_MTOTAL%"
    };

    public static String[] apex_params = new String[]{
            "%MAKER_DAY1%",
            "%MAKER_DAY2%",
            "%MAKER_WEEK1%",
            "%MAKER_WEEK2%",
            "%PASS_END%",
            "%SHOP_REFRESH%",
            "%PW_RP_NOWMAP%",
            "%PW_RP_OTHERMAP%",
            "%PW_RP_ENDTIME%",
            "%PW_AP_NOWMAP%",
            "%PW_AP_NEXMAP%",
            "%PW_AP_ENDTIME%",
            "%PP_RP_NOWMAP%",
            "%PP_RP_NEXMAP%",
            "%PP_RP_ENDTIME%",
            "%PP_AP_NOWMAP%",
            "%PP_AP_NEXMAP%",
            "%PP_AP_ENDTIME%"
    };



}
