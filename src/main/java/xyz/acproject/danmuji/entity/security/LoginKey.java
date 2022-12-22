package xyz.acproject.danmuji.entity.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName LoginKey
 * @Description TODO
 * @date 2021/6/16 0:11
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginKey implements Serializable {
    private static final long serialVersionUID = 4676755603041887887L;
    private String key;

}
