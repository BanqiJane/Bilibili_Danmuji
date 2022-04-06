package xyz.acproject.danmuji.conf.set;

/**
 * @author Jane
 * @ClassName PrivacySetConf
 * @Description 隐私设置 不再请求服务器
 * @date 2022/3/24 14:27
 * @Copyright:2022
 */
public class PrivacySetConf {
    //是否开启
    private boolean is_open = false;
    //必须得满足弹幕姬的请求参数 和 返回参数的小心心s参数地址
    private String small_heart_url = "";


    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public String getSmall_heart_url() {
        return small_heart_url;
    }

    public void setSmall_heart_url(String small_heart_url) {
        this.small_heart_url = small_heart_url;
    }
}
