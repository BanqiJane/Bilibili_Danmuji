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

    @JSONField(name = "all")
    private boolean all;

    @JSONField(name = "thank_gift")
    private boolean thank_gift;
    @JSONField(name = "thank_welcome")
    private boolean thank_welcome;
    @JSONField(name = "thank_follow")
    private boolean thank_follow;
    @JSONField(name = "auto_reply")
    private boolean auto_reply;
    @JSONField(name = "fuzzy_query")
    private boolean fuzzy_query;
    @JSONField(name = "names")
    private HashSet<String> names;
    @JSONField(name = "uids")
    private HashSet<String> uids;

    public HashSet<String> getNames() {
        if(names==null)return new HashSet<>();
        return names;
    }

    public HashSet<String> getUids() {
        if(uids!=null){
            return uids.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return new HashSet<>();
    }
}
