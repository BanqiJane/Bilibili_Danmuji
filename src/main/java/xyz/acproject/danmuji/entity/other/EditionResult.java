package xyz.acproject.danmuji.entity.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName Edition
 * @Description TODO
 * @date 2021/7/19 23:33
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditionResult implements Serializable {
    private static final long serialVersionUID = 7338368414549319875L;
    private int status;
    private String edition;
    private String url;
}
