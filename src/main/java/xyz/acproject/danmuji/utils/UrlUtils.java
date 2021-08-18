package xyz.acproject.danmuji.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author Jane
 * @ClassName UrlUtils
 * @Description TODO
 * @date 2021/3/29 16:57
 * @Copyright:2021
 */
public class UrlUtils {

    public static String getBaseUrl(HttpServletRequest request){
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();
        return tempContextUrl;
    }

    public static String URLEncoderString(String str,String charset) {
        String result = "";
        if (StringUtils.isBlank(str)) {
            return "";
        }
        if(StringUtils.isBlank(charset)){
            charset = "UTF-8";
        }
        try {
            result = java.net.URLEncoder.encode(str, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String URLDecoderString(String str,String charset) {
        String result = "";
        if (StringUtils.isBlank(str)) {
            return "";
        }
        if(StringUtils.isBlank(charset)){
            charset = "UTF-8";
        }
        try {
            result = java.net.URLDecoder.decode(str, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
