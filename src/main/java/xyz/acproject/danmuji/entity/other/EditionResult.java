package xyz.acproject.danmuji.entity.other;

import java.io.Serializable;

/**
 * @author Jane
 * @ClassName Edition
 * @Description TODO
 * @date 2021/7/19 23:33
 * @Copyright:2021
 */
public class EditionResult implements Serializable {
    private static final long serialVersionUID = 7338368414549319875L;
    private int status;
    private String edition;

    public EditionResult() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}
