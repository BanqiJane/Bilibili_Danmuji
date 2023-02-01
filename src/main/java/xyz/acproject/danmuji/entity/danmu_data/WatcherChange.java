package xyz.acproject.danmuji.entity.danmu_data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Admin
 * @ClassName WatcherChange
 * @Description TODO
 * @date 2023/1/29 14:25
 * @Copyright:2023
 */
@Data
public class WatcherChange implements Serializable {
    private long num;

    private String text_small;

    private String text_large;
}
