package xyz.acproject.danmuji.utils;

import org.springframework.util.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Admin
 * @ClassName WbiSignUtils
 * @Description TODO
 * @date 2025/5/27 14:41
 * @Copyright:2025
 */
public class WbiSignUtils {

    private static final int[] MIXIN_KEY_ENC_TAB = {
            46, 47, 18, 2, 53, 8, 23, 32, 15, 50,
            10, 31, 58, 3, 45, 35, 27, 43, 5, 49,
            33, 9, 42, 19, 29, 28, 14, 39, 12, 38,
            41, 13, 37, 48, 7, 16, 24, 55, 40, 61,
            26, 17, 0, 1, 60, 51, 30, 4, 22, 25,
            54, 21, 56, 59, 6, 63, 57, 62, 11, 36,
            20, 34, 44, 52
    };

    // 对 imgKey 和 subKey 进行字符顺序打乱编码
    public static String getMixinKey(String orig) {
        StringBuilder temp = new StringBuilder();
        for (int n : MIXIN_KEY_ENC_TAB) {
            if (n < orig.length()) {
                temp.append(orig.charAt(n));
            }
        }
        return temp.toString().substring(0, Math.min(32, temp.length()));
    }

    public static String getWbiSign(Map<String, String> params, String imgKey, String subKey) {

        // 1. 混合imgKey和subKey
        String key = imgKey + subKey;
        String mixinKey = getMixinKey(key);
        // 2. 参数排序并拼接
        Map<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (query.length() > 0) {
                query.append("&");
            }
            query.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return DigestUtils.md5DigestAsHex((query + mixinKey).getBytes());
    }
}
