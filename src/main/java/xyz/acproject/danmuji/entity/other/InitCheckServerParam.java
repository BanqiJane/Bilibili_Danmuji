package xyz.acproject.danmuji.entity.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jane
 * @ClassName InitCheckServerParam
 * @Description TODO
 * @date 2021/7/20 23:00
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitCheckServerParam {
    private boolean init_edition;
    private boolean init_announce;

}
