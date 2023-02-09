package xyz.acproject.danmuji.conf.set;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import xyz.acproject.danmuji.conf.base.OpenSetConf;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * @author Admin
 * @ClassName BlackListSetConf
 * @Description TODO
 * @date 2023/1/13 10:09
 * @Copyright:2023
 */
@Data
public class BlackListSetConf{

    private boolean all;

    private boolean thank_gift;

    private boolean thank_welcome;

    private boolean thank_follow;

    private boolean auto_reply;

    private HashSet<String> names;

    private HashSet<String> uids;

    public HashSet<String> getNames() {
        return names;
    }

    public HashSet<String> getUids() {
        if(uids!=null){
            return uids.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return uids;
    }
}
