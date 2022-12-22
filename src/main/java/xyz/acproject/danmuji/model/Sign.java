package xyz.acproject.danmuji.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Jane
 * @ClassName Sign
 * @Description TODO
 * @date 2022/7/7 12:15
 * @Copyright:2022
 */
@Data
public class Sign {
    private Integer id;
    private Long roomId;
    private Long uId;
    private Integer signDay;
    private Date signDate;
}
