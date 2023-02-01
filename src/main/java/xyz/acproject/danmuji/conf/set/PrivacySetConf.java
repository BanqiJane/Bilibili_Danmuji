package xyz.acproject.danmuji.conf.set;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.acproject.danmuji.conf.base.OpenSetConf;

/**
 * @author Jane
 * @ClassName PrivacySetConf
 * @Description 隐私设置 不再请求服务器
 * @date 2022/3/24 14:27
 * @Copyright:2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivacySetConf extends OpenSetConf {

    //必须得满足弹幕姬的请求参数 和 返回参数的小心心s参数地址
    private String small_heart_url = "http://biliheart-1.herokuapp.com/enc";


    private int signDay=0;

    private int clockInDay=0;

}
