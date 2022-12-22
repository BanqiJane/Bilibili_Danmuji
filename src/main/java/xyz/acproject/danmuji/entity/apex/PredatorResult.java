package xyz.acproject.danmuji.entity.apex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jane
 * @ClassName PredatorResult
 * @Description TODO
 * @date 2022/10/7 23:23
 * @Copyright:2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PredatorResult {

    private String game_type;
    private String plate;
    private Integer foundRank;
    private Integer val;
    private String uid;
    private Long updateTimestamp;
    private Integer totalMastersAndPreds;

}
