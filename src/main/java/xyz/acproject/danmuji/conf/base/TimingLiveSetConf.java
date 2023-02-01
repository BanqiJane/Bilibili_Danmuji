package xyz.acproject.danmuji.conf.base;

import lombok.Data;

/**
 * @author Admin
 * @ClassName TimingLiveSetConf
 * @Description TODO
 * @date 2023/1/13 9:15
 * @Copyright:2023
 */
@Data
public abstract class TimingLiveSetConf extends LiveSetConf{

    private double time=0;
}
