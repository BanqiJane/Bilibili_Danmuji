package xyz.acproject.danmuji.entity.user_data;

/**
 * @author Jane
 * @ClassName UserBagCount
 * @Description TODO
 * @date 2021/7/24 23:10
 * @Copyright:2021
 */
public class UserBagCount {

    private Integer num;
    private String text;

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "UserBagCount{" +
                "num=" + num +
                ", text='" + text + '\'' +
                '}';
    }
}
