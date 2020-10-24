package xyz.acproject.danmuji.utils;

import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ReflectionUtils;

/**
 * @ClassName SchedulingRunnableUtil
 * @Description TODO
 * @author BanqiJane
 * @date 2020年9月5日 下午11:51:10
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
@SuppressWarnings("all")
public class SchedulingRunnableUtil implements Runnable {
	
	private static final Logger LOGGER  = LogManager.getLogger(SchedulingRunnableUtil.class);
	
	private String beanName;

    private String methodName;

    private Object[] params;
    
    
    public SchedulingRunnableUtil(String beanName, String methodName) {
        this(beanName, methodName,new Object());
    }

    public SchedulingRunnableUtil(String beanName, String methodName, Object...params ) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
    }
    
    @Override
    public void run() {
    	LOGGER.info("定时任务开始执行 - bean：{}，方法：{}，参数：{}", beanName, methodName, params);
        long startTime = System.currentTimeMillis();

        try {
            Object target = SpringUtils.getBean(beanName);

            Method method = null;
            if (null != params && params.length > 0) {
                Class<?>[] paramCls = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    paramCls[i] = params[i].getClass();
                }
                method = target.getClass().getDeclaredMethod(methodName, paramCls);
            } else {
                method = target.getClass().getDeclaredMethod(methodName);
            }

            ReflectionUtils.makeAccessible(method);
            if (null != params && params.length > 0) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception ex) {
        	LOGGER.error(String.format("定时任务执行异常 - bean：%s，方法：%s，参数：%s ", beanName, methodName, params), ex);
        }

        long times = System.currentTimeMillis() - startTime;
        LOGGER.info("定时任务执行结束 - bean：{}，方法：{}，参数：{}，耗时：{} 毫秒", beanName, methodName, params, times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingRunnableUtil that = (SchedulingRunnableUtil) o;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                params.equals(that.params);
    }

    @Override
    public int hashCode() {
        if (params == null) {
            return Objects.hash(beanName, methodName);
        }

        return Objects.hash(beanName, methodName, params);
    }

}
