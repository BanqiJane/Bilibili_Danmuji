package xyz.acproject.danmuji.entity.user_data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Admin
 * @ClassName UserNav
 * @Description TODO
 * @date 2025/5/27 14:33
 * @Copyright:2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNav {

    private WbiImg wbiImg;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public final static class WbiImg{
        private String imgUrl;
        private String subUrl;
    }
}
