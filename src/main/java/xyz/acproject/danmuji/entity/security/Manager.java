package xyz.acproject.danmuji.entity.security;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName manager
 * @Description TODO
 * @date 2021/6/13 23:30
 * @Copyright:2021
 */
public class Manager implements Serializable {
    private String key;
    private int max_size;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMax_size() {
        return max_size;
    }

    public void setMax_size(int max_size) {
        this.max_size = max_size;
    }
}
