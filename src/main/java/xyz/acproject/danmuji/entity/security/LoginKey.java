package xyz.acproject.danmuji.entity.security;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName LoginKey
 * @Description TODO
 * @date 2021/6/16 0:11
 * @Copyright:2021
 */
public class LoginKey implements Serializable {
    private static final long serialVersionUID = 4676755603041887887L;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
