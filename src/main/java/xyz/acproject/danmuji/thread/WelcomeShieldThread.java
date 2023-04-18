package xyz.acproject.danmuji.thread;

import lombok.Getter;
import lombok.Setter;
import xyz.acproject.danmuji.conf.PublicDataConf;

/**
 * @author Jane
 * @ClassName WelcomeShieldThread
 * @Description TODO
 * @date 2021/4/15 0:11
 * @Copyright:2021
 */
@Getter
@Setter
public class WelcomeShieldThread extends Thread{
    public volatile boolean FLAG = false;
    private int time = 300;

    @Override
    public void run() {
        // TODO 自动生成的方法存根
        super.run();
        if (FLAG) {
            return;
        }
        if(PublicDataConf.webSocketProxy!=null&&!PublicDataConf.webSocketProxy.isOpen()) {
            return;
        }
        PublicDataConf.ISSHIELDWELCOME = true;
        try {
            Thread.sleep(time * 1000);
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
//			e.printStackTrace();
        }
        PublicDataConf.ISSHIELDWELCOME = false;
    }
}
