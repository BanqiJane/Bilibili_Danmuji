package xyz.acproject.danmuji.thread;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.entity.danmu_data.Interact;

import java.util.Vector;

/**
 * @author Jane
 * @ClassName PraseThankWelcomeThread
 * @Description TODO
 * @date 2021/4/12 23:32
 * @Copyright:2021
 */
@Getter
@Setter
public class ParseThankWelcomeThread extends Thread{
    public volatile boolean FLAG = false;
    private String thankWelcomeString = "欢迎%uNames%进入直播间~";
    private Short num = 1;
    private Long delaytime = 3000L;
    private Long timestamp;
    @Override
    public void run() {
        super.run();
        String thankWelcomeStr = null;
        StringBuilder stringBuilder = new StringBuilder(300);
        Vector<Interact> interacts = new Vector<Interact>();
        synchronized (timestamp) {
            while (!FLAG) {
                if (FLAG) {
                    return;
                }
                if(PublicDataConf.webSocketProxy!=null&&!PublicDataConf.webSocketProxy.isOpen()) {
                    return;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO 自动生成的 catch 块
                }
                long nowTime = System.currentTimeMillis();
                if (nowTime - getTimestamp() < getDelaytime()) {
                } else {
                    //do something
                    if(PublicDataConf.interactWelcome.size()>0) {
                        interacts.addAll(PublicDataConf.interactWelcome);
                        for (int i = 0; i < interacts.size(); i += getNum()) {
                            for (int j = i; j < i + getNum(); j++) {
                                if (j >= interacts.size()) {
                                    break;
                                }
                                stringBuilder.append(interacts.get(j).getUname()).append(",");
                            }
                            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());

                            thankWelcomeStr =StringUtils.replace(handleThankStr(getThankWelcomeString()), "%uNames%", stringBuilder.toString());
                            stringBuilder.delete(0, stringBuilder.length());
                            if (PublicDataConf.sendBarrageThread != null
                                    && !PublicDataConf.sendBarrageThread.FLAG) {
                                PublicDataConf.barrageString.add(thankWelcomeStr);
                                synchronized (PublicDataConf.sendBarrageThread) {
                                    PublicDataConf.sendBarrageThread.notify();
                                }
                            }
                            thankWelcomeStr = null;
                        }
                    }
                    interacts.clear();
                    PublicDataConf.interactWelcome.clear();
                    break;
                }
            }
        }
    }

    public String handleThankStr(String thankStr) {
        String thankWelcomeStrs[] = null;
        if (StringUtils.indexOf(thankStr, "\n") != -1) {
            thankWelcomeStrs = StringUtils.split(thankStr, "\n");
        }
        if(thankWelcomeStrs!=null&&thankWelcomeStrs.length>1) {
            return thankWelcomeStrs[(int) Math.ceil(Math.random() * thankWelcomeStrs.length)-1];
        }
        return thankStr;
    }
}
