package xyz.acproject.danmuji.conf;

import cn.hutool.cache.impl.TimedCache;

/**
 * @author Admin
 * @ClassName CacheConf
 * @Description TODO
 * @date 2023/10/18 15:00
 * @Copyright:2023
 */
public class CacheConf {
    public final static TimedCache<String, Object> globalCache = new TimedCache<>(1000*60);


    public static boolean existRedPackageCache(Long roomId){
        String key = "redPackage:shield:"+roomId;
        return globalCache.containsKey(key);
    }

    public static void setRedPackageCache(Long roomId,Long mills){
        String key = "redPackage:shield:"+roomId;
        globalCache.put(key,roomId,mills);
    }

    public static boolean existTx(Long roomId){
        String key = "tx:shield:"+roomId;
        return globalCache.containsKey(key);
    }

    public static String getTx(Long roomId){
        String key = "tx:shield:"+roomId;
        return (String) globalCache.get(key,false);
    }

    public static void setTX(Long roomId,String giftName,Long mills){
        String key = "tx:shield:"+roomId;
        globalCache.put(key,giftName,mills);
    }

}
