package xyz.acproject.danmuji.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jane
 * @ClassName XmlUtils
 * @Description TODO
 * @date 2022/4/24 17:01
 * @Copyright:2022
 */
public class XmlUtils {
    public  static <T> T getData(String xml, Class<T> clazz) {
        if(null==xml||"".equals(xml)){
            return null;
        }
        Pattern p = Pattern.compile(".*<!\\[CDATA\\[(.*)\\]\\]>.*");
        Matcher m = p.matcher(xml);
        if(m.matches()) {
            return clazz.cast(m.group(1));
        }
        return null;
    }
}
