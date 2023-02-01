package xyz.acproject.danmuji.conf.set;

import lombok.Data;
import xyz.acproject.danmuji.conf.base.OpenSetConf;

import java.util.HashSet;

/**
 * @author Admin
 * @ClassName BlackListSetConf
 * @Description TODO
 * @date 2023/1/13 10:09
 * @Copyright:2023
 */
@Data
public class BlackListSetConf extends OpenSetConf {
    private HashSet<String> names;
    private HashSet<String> uids;


}
