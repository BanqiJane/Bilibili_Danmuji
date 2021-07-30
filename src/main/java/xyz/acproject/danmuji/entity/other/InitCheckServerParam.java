package xyz.acproject.danmuji.entity.other;

/**
 * @author Jane
 * @ClassName InitCheckServerParam
 * @Description TODO
 * @date 2021/7/20 23:00
 * @Copyright:2021
 */
public class InitCheckServerParam {
    private boolean init_edition;
    private boolean init_announce;

    public boolean isInit_edition() {
        return init_edition;
    }

    public void setInit_edition(boolean init_edition) {
        this.init_edition = init_edition;
    }

    public boolean isInit_announce() {
        return init_announce;
    }

    public void setInit_announce(boolean init_announce) {
        this.init_announce = init_announce;
    }
}
