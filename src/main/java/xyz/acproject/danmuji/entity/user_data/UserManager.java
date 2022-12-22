package xyz.acproject.danmuji.entity.user_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName UserManager
 * @Description TODO
 * @date 2021/4/19 22:42
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManager implements Serializable {
    private static final long serialVersionUID = -6419840093508270008L;
    private Long roomid;
    private Long short_roomid;
    private boolean is_manager;

}
