package xyz.acproject.danmuji.entity.room_data;

import lombok.Data;

/**
 * @author Admin
 * @ClassName RoomInfoAnchor
 * @Description TODO
 * @date 2023/2/6 15:57
 * @Copyright:2023
 */
@Data
public class RoomInfoAnchor {
    private RoomInfo roomInfo;

    private MedalInfoAnchor medalInfoAnchor;
}
