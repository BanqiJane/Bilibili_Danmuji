package xyz.acproject.danmuji.entity.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName manager
 * @Description TODO
 * @date 2021/6/13 23:30
 * @Copyright:2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager implements Serializable {
    private String key;
    private int max_size;

}
