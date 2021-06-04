package xyz.acproject.danmuji.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

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
        this(beanName, methodName, new  Object[]{});
    }

    public SchedulingRunnableUtil(String beanName, String methodName, Object...params ) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
    }
    
    @Override
    public void run() {
        synchronized (methodName) {
            LOGGER.info("定时任务开始执行 - bean：{}，方法：{}，参数：{}", beanName, methodName, params);
            long startTime = System.currentTimeMillis();

            try {
                Object target = SpringUtils.getBean(beanName);
                Class clazz = ByteUtils.class;
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        SchedulingRunnableUtil that = (SchedulingRunnableUtil) o;
        if (this.params == null) {
            return this.beanName.equals(that.beanName) &&
                    this.methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return this.beanName.equals(that.beanName) &&
                this.methodName.equals(that.methodName) &&
                Arrays.equals(this.params,that.params);
    }



    @Override
    public int hashCode() {
//        if (this.params == null||this.params.length<=0) {
//            return Objects.hash(this.beanName, this.methodName);
//        }
//        return Objects.hash(this.beanName, this.methodName,  Arrays.hashCode(this.params));
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBeanName() == null) ? 0 : getBeanName().hashCode());
        result = prime * result + ((getMethodName() == null) ? 0 : getMethodName().hashCode());
        result = prime * result + (((this.params == null) ? 0 :Arrays.hashCode(this.params)));
        return result;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
